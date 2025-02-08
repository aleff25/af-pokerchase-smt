package br.com.af.pokerchase.dto;

import java.math.BigInteger;

public record PokerActionDTO(
  String action,
  BigInteger amount,
  String playerAddress
) {
}
