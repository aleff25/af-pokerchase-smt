package br.com.af.pokerchase.common.exception;

public class InsufficientBalanceException extends RuntimeException {
  public InsufficientBalanceException() {
    super("Saldo insuficiente");
  }
}
