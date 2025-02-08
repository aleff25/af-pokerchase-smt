package br.com.af.pokerchase.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class GameState {
  private String gameId;
  private Phase currentPhase;
  private List<String> players;
  private List<String> communityCards = new ArrayList<>();
  private Map<String, BigInteger> balances = new HashMap<>();
  private Map<String, String> holeCards = new HashMap<>();
  private String currentPlayer;
  private BigInteger pot = BigInteger.ZERO;

  public void applyAction(String playerAddress, PokerAction action) {
    switch (action.getType()) {
      case FOLD -> {
        players.remove(playerAddress);
        if (players.size() == 1) {
          currentPlayer = null;
          currentPhase = Phase.SHOWDOWN;
        } else {
          int playerIndex = players.indexOf(playerAddress);
          if (playerIndex == players.size() - 1) {
            currentPlayer = players.get(0);
          } else {
            currentPlayer = players.get(playerIndex + 1);
          }
        }
      }
      case CHECK, CALL -> {
        balances.put(playerAddress, balances.get(playerAddress).subtract(action.getAmount()));
        pot = pot.add(action.getAmount());
        int playerIndex = players.indexOf(playerAddress);
        if (playerIndex == players.size() - 1) {
          currentPlayer = players.get(0);
        } else {
          currentPlayer = players.get(playerIndex + 1);
        }
      }
      case RAISE -> {
        balances.put(playerAddress, balances.get(playerAddress).subtract(action.getAmount()));
        pot = pot.add(action.getAmount());
        int playerIndex = players.indexOf(playerAddress);
        if (playerIndex == players.size() - 1) {
          currentPlayer = players.get(0);
        } else {
          currentPlayer = players.get(playerIndex + 1);
        }
      }
    }
  }

  public enum Phase {REGISTRATION, PREFLOP, FLOP, TURN, RIVER, SHOWDOWN}
}
