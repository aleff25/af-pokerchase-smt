package br.com.af.pokerchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class PokerAction {
  private ActionType type;
  private BigInteger amount;
  private String playerAddress;
  public enum ActionType {FOLD, CHECK, CALL, RAISE}
}
