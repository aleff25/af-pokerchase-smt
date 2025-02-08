package br.com.af.pokerchase.controller;

import br.com.af.pokerchase.dto.ActionResponseDTO;
import br.com.af.pokerchase.dto.GameInitRequestDTO;
import br.com.af.pokerchase.dto.GameStateDTO;
import br.com.af.pokerchase.dto.PhaseAdvanceRequestDTO;
import br.com.af.pokerchase.dto.PlayerActionRequestDTO;
import br.com.af.pokerchase.service.BlockchainService;
import br.com.af.pokerchase.service.PokerGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

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

  @MessageMapping("/game/initialize")
  @SendTo("/topic/game-state")
  public GameStateDTO initializeGame(@RequestBody GameInitRequestDTO request) throws Exception {
    gameService.initializeGame(
      request.getGameId(),
      request.getPlayers(),
      request.getSmallBlind(),
      request.getBigBlind()
    );
    return gameService.getGameState(request.getGameId());
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
    gameService.advanceGamePhase(request.getGameId());
  }

  private void broadcastGameState(String gameId) {
    GameStateDTO state = gameService.getGameState(gameId);
    messagingTemplate.convertAndSend("/topic/game/" + gameId, state);
  }
}
