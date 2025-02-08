package br.com.af.pokerchase.service;

import br.com.af.pokerchase.common.exception.GameNotEndedException;
import br.com.af.pokerchase.common.exception.GameNotFoundException;
import br.com.af.pokerchase.common.exception.InsufficientBalanceException;
import br.com.af.pokerchase.common.exception.PlayerNotFoundException;
import br.com.af.pokerchase.domain.ActionType;
import br.com.af.pokerchase.domain.Deck;
import br.com.af.pokerchase.domain.Game;
import br.com.af.pokerchase.domain.GameActionLog;
import br.com.af.pokerchase.domain.GameState;
import br.com.af.pokerchase.domain.GameType;
import br.com.af.pokerchase.domain.Player;
import br.com.af.pokerchase.domain.Rank;
import br.com.af.pokerchase.domain.Suit;
import br.com.af.pokerchase.dto.CardDTO;
import br.com.af.pokerchase.dto.ContractEvent;
import br.com.af.pokerchase.dto.GameDTO;
import br.com.af.pokerchase.dto.GameStateDTO;
import br.com.af.pokerchase.dto.PlayerDTO;
import br.com.af.pokerchase.repository.GameActionLogRepository;
import br.com.af.pokerchase.repository.GameRepository;
import br.com.af.pokerchase.repository.TournamentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PokerGameService {

  private final GameRepository gameRepository;
  private final TournamentRepository tournamentRepository;
  private final GameActionLogRepository logRepository;
  private final BlockchainService blockchainService;
  private final SimpMessagingTemplate messagingTemplate;

  @PostConstruct
  public void init() {
    blockchainService.listenToGameEvents(this::handleBlockchainEvent);
  }

  private void handleBlockchainEvent(ContractEvent event) {
    switch (event.getType()) {
      case GAME_STARTED -> handleGameStarted(event);
      case CARDS_DEALT -> handleCardsDealt(event);
      case PLAYER_ACTION -> handlePlayerActionFromChain(event);
      case GAME_ENDED -> handleGameEnded(event);
    }
  }

  private void handleGameStarted(ContractEvent event) {
    String gameId = event.getGameId();
    GameState game = activeGames.get(gameId);
    if (game != null) {
      game.transitionTo(GameState.Phase.PREFLOP);
      broadcastGameState(gameId);
    }
  }

  private void handleCardsDealt(ContractEvent event) {
    String gameId = event.getGameId();
    GameState game = activeGames.get(gameId);
    if (game != null) {
      game.setCommunityCards(event.getCommunityCards());
      game.transitionToNextPhase();
      broadcastGameState(gameId);
    }
  }

  private void broadcastGameState(String gameId) {
    GameState game = activeGames.get(gameId);
    if (game != null) {
      messagingTemplate.convertAndSend("/topic/game/" + gameId, game);
    }
  }

  private void handleFailedTransaction(String gameId, TransactionReceipt receipt) {
    // Implementar rollback ou compensação
    GameState game = activeGames.get(gameId);
    game.revertLastAction();
    broadcastGameState(gameId);
  }

  private void sendErrorToClient(String playerAddress, String message) {
    messagingTemplate.convertAndSendToUser(playerAddress, "/queue/errors", message);
  }

  // Cria um novo jogo
  public GameDTO createGame(GameType type, List<String> playerIds) {
    Game game = new Game();
    game.setId(UUID.randomUUID().toString());
    game.setType(type);
    game.setPlayers(createPlayers(playerIds));
    game.setState(GameState.WAITING);
    game.setCreatedAt(Instant.now());
    gameRepository.save(game);
    return convertToDTO(game);
  }

  // Inicia um jogo existente
  public void startGame(String gameId) {
    Game game = getGameEntity(gameId);

    Deck deck = new Deck();
    game.setDeckCards(deck.getCards());

    // Distribui cartas para os jogadores
    for (Player player : game.getPlayers()) {
      List<String> hand = new ArrayList<>();
      hand.add(game.draw()); // Primeira carta
      hand.add(game.draw()); // Segunda carta
      player.setHand(hand);
    }

    game.setState(GameState.PREFLOP);
    gameRepository.save(game);
  }

  // Processa uma aposta
  public void processBet(String gameId, String playerId, int amount) {
    Game game = getGameEntity(gameId);
    Player player = findPlayer(game, playerId);

    if (player.getBalance() < amount) {
      throw new InsufficientBalanceException();
    }

    player.setBalance(player.getBalance() - amount);
    game.setPot(game.getPot() + amount);
    gameRepository.save(game);
    logAction(gameId, playerId, "BET: " + amount);
  }

  // Processa um fold
  public void processFold(String gameId, String playerId) {
    Game game = getGameEntity(gameId);
    Player player = findPlayer(game, playerId);
    player.setFolded(true);
    gameRepository.save(game);
    logAction(gameId, playerId, "FOLD");
  }

  // Finaliza um jogo
  public void endGame(String gameId) {
    Game game = getGameEntity(gameId);
    game.setState(GameState.ENDED);
    gameRepository.save(game);
    logAction(gameId, "SYSTEM", "Game ended");
  }

  // Determina o vencedor
  public String determineWinner(String gameId) {
    Game game = getGameEntity(gameId);
    return game.getPlayers().stream()
      .filter(p -> !p.isFolded())
      .findFirst()
      .orElseThrow(GameNotEndedException::new)
      .getId();
  }

  public BigInteger getPot(String gameId) {
    Game game = getGameEntity(gameId);
    return BigInteger.valueOf(game.getPot());
  }

  public GameStateDTO getGameState(String gameId) {
    Game game = getGameEntity(gameId);
    return new GameStateDTO(
      game.getId(),
      game.getState(),
      game.getPot(),
      convertCardsToDTO(game.getCommunityCards())
    );
  }

  // --- Métodos Auxiliares ---
  private Game getGameEntity(String gameId) {
    return gameRepository.findById(gameId)
      .orElseThrow(() -> new GameNotFoundException(gameId));
  }

  private Player findPlayer(Game game, String playerId) {
    return game.getPlayers().stream()
      .filter(p -> p.getId().equals(playerId))
      .findFirst()
      .orElseThrow(() -> new PlayerNotFoundException(playerId));
  }

  private List<Player> createPlayers(List<String> playerIds) {
    return playerIds.stream()
      .map(id -> {
        Player player = new Player();
        player.setId(id);
        player.setWalletAddress(id);
        player.setBalance(1000); // Saldo inicial
        player.setFolded(false);
        return player;
      })
      .toList();
  }

  private void dealInitialCards(Game game) {
    Deck deck = new Deck(); // Novo baralho para cada jogo
    game.setDeckCards(deck.getCards());     // Armazena o deck no jogo (se necessário para etapas futuras)

    for (Player player : game.getPlayers()) {
      List<String> hand = new ArrayList<>();
      hand.add(deck.drawCard()); // Primeira carta
      hand.add(deck.drawCard()); // Segunda carta
      player.setHand(hand);
    }
  }

  public void dealCommunityCards(String gameId, int count) {
    Game game = getGameEntity(gameId);
    List<String> communityCards = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      communityCards.add(game.draw());
    }

    game.setCommunityCards(communityCards);
    gameRepository.save(game);
  }

  private void logAction(String gameId, String playerId, String details) {
    GameActionLog log = new GameActionLog();
    log.setGameId(gameId);
    log.setPlayerId(playerId);
    log.setActionType(ActionType.valueOf(details.split(":")[0]));
    log.setDetails(details);
    log.setTimestamp(Instant.now());
    logRepository.save(log);
  }

  private GameDTO convertToDTO(Game game) {
    return new GameDTO(
      game.getId(),
      game.getType(),
      convertPlayersToDTO(game.getPlayers()),
      convertCardsToDTO(game.getCommunityCards()),
      game.getState(),
      game.getPot(),
      game.getCurrentBet()
    );
  }

  private List<PlayerDTO> convertPlayersToDTO(List<Player> players) {
    return players.stream()
      .map(p -> new PlayerDTO(
        p.getId(),
        p.getWalletAddress(),
        p.getBalance(),
        convertCardsToDTO(p.getHand()),
        p.isFolded()
      ))
      .toList();
  }

  private List<CardDTO> convertCardsToDTO(List<String> cardStrings) {
    return cardStrings.stream()
      .map(s -> {
        String[] parts = s.split("_");
        return new CardDTO(
          Suit.valueOf(parts[0]),
          Rank.valueOf(parts[1])
        );
      })
      .toList();
  }
}