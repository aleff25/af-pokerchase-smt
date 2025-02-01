package br.com.af.pokerchase.common.exception;

public class GameNotEndedException extends RuntimeException {
  public GameNotEndedException() {
    super("O jogo ainda não terminou");
  }
}