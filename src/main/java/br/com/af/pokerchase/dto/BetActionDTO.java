package br.com.af.pokerchase.dto;

import java.math.BigInteger;

public record BetActionDTO(
  String gameId,
  String playerId,
  BigInteger amount
) {
}
