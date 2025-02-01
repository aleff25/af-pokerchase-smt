package br.com.af.pokerchase.common.exception;

public class TournamentNotFoundException extends RuntimeException {

  public TournamentNotFoundException(String message) {
    super(message);
  }
}
