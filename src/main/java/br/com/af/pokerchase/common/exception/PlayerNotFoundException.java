package br.com.af.pokerchase.common.exception;

public class PlayerNotFoundException extends RuntimeException {

  public PlayerNotFoundException(String playerId) {
    super(String.format("Player não encontrado: %s", playerId));
  }
}
