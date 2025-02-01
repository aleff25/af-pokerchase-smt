package br.com.af.pokerchase.controller;

import br.com.af.pokerchase.dto.BetActionDTO;
import br.com.af.pokerchase.dto.FoldActionDTO;
import br.com.af.pokerchase.dto.GameEndRequestDTO;
import br.com.af.pokerchase.dto.GameStartRequestDTO;
import br.com.af.pokerchase.dto.GameStateDTO;
import br.com.af.pokerchase.service.BlockchainService;
import br.com.af.pokerchase.service.PokerGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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

  private void broadcastGameState(String gameId) {
    GameStateDTO state = gameService.getGameState(gameId);
    messagingTemplate.convertAndSend("/topic/game/" + gameId, state);
  }
}
