package br.com.af.pokerchase.common.exception;

public class GameNotFoundException extends RuntimeException {
  public GameNotFoundException(String gameId) {
    super("Game not found with ID: " + gameId);
  }
}
