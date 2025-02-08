package br.com.af.pokerchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ContractEvent {
  private EventType type;
  private String gameId;
  private List<String> players;
  private List<String> communityCards;
  private PokerAction action;
  public enum EventType {GAME_STARTED, CARDS_DEALT, PLAYER_ACTION, GAME_ENDED}
}
