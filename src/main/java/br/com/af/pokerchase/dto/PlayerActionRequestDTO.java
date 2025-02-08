package br.com.af.pokerchase.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PlayerActionRequestDTO {
  private String gameId;
  private String playerAddress;
  private String actionType; // "FOLD", "CALL", "RAISE"
  private BigInteger amount;
}