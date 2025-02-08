package br.com.af.pokerchase.controller;

import br.com.af.pokerchase.domain.GameState;
import br.com.af.pokerchase.dto.ActionResponseDTO;
import br.com.af.pokerchase.dto.BetActionDTO;
import br.com.af.pokerchase.dto.FoldActionDTO;
import br.com.af.pokerchase.dto.GameEndRequestDTO;
import br.com.af.pokerchase.dto.GameInitRequestDTO;
import br.com.af.pokerchase.dto.GameStartRequestDTO;
import br.com.af.pokerchase.dto.GameStateDTO;
import br.com.af.pokerchase.dto.PhaseAdvanceRequestDTO;
import br.com.af.pokerchase.dto.PlayerActionRequestDTO;
import br.com.af.pokerchase.service.BlockchainService;
import br.com.af.pokerchase.service.PokerGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;

@Controller
public class PokerWebSocketController {

  private final SimpMessagingTemplate messagingTemplate;
  private final PokerGameService gameService;
  private final BlockchainService blockchainService;

  @Autowired
  public PokerWebSocketController(
    SimpMessagingTemplate messagingTemplate,
    PokerGameService gameService,
    BlockchainService blockchainService
  ) {
    this.messagingTemplate = messagingTemplate;
    this.gameService = gameService;
    this.blockchainService = blockchainService;
  }

  // Iniciar uma partida
  @MessageMapping("/game/start")
  public void startGame(
    @Header("Authorization") String token,
    GameStartRequestDTO request
  ) {
    String gameId = request.gameId();
    gameService.startGame(gameId);
    broadcastGameState(gameId);
  }

  // Apostar
  @MessageMapping("/game/bet")
  public void handleBet(
    @Header("Authorization") String token,
    BetActionDTO action
  ) throws Exception {
    String gameId = action.gameId();
    String playerId = action.playerId();
    BigInteger amount = action.amount();

    // Processa a aposta no blockchain
    blockchainService.processBet(playerId, amount);

    // Atualiza o estado do jogo
    gameService.processBet(gameId, playerId, amount.intValue());
    broadcastGameState(gameId);
  }

  // Fold
  @MessageMapping("/game/fold")
  public void handleFold(
    @Header("Authorization") String token,
    FoldActionDTO action
  ) {
    String gameId = action.gameId();
    String playerId = action.playerId();

    gameService.processFold(gameId, playerId);
    broadcastGameState(gameId);
  }

  // Finalizar partida (quando resta 1 jogador)
  @MessageMapping("/game/end")
  public void endGame(
    @Header("Authorization") String token,
    GameEndRequestDTO request
  ) throws Exception {
    String gameId = request.gameId();
    String winnerId = gameService.determineWinner(gameId);

    // Distribui o prêmio via blockchain
    blockchainService.distributePrize(winnerId, gameService.getPot(gameId));

    // Encerra a partida
    gameService.endGame(gameId);
    broadcastGameState(gameId);
  }

  @MessageMapping("/game/initialize")
  @SendTo("/topic/game-state")
  public GameState initializeGame(@RequestBody GameInitRequestDTO request) throws Exception {
    pokerGameService.initializeGame(
      request.getGameId(),
      request.getPlayers(),
      request.getSmallBlind(),
      request.getBigBlind()
    );
    return pokerGameService.getGameState(request.getGameId());
  }

  // Endpoint para ações dos jogadores
  @MessageMapping("/player/action")
  @SendTo("/topic/actions")
  public ActionResponseDTO handlePlayerAction(@Payload PlayerActionRequestDTO action) {
    try {
      blockchainService.submitPlayerAction(
        action.getGameId(),
        action.getPlayerAddress(),
        action.getActionType(),
        action.getAmount()
      );
      return new ActionResponse(true, "Ação processada");
    } catch (Exception e) {
      return new ActionResponse(false, "Erro: " + e.getMessage());
    }
  }

  // Endpoint para forçar avanço de fase (debug)
  @MessageMapping("/game/advance-phase")
  public void advancePhase(@Payload PhaseAdvanceRequestDTO request) {
    pokerGameService.advanceGamePhase(request.getGameId());
  }

  private void broadcastGameState(String gameId) {
    GameStateDTO state = gameService.getGameState(gameId);
    messagingTemplate.convertAndSend("/topic/game/" + gameId, state);
  }
}
