package br.com.af.pokerchase.contract;

import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class PokerGameContract extends Contract {

  public PokerGameContract(
    String contractAddress,
    Web3j web3j,
    Credentials credentials,
    DefaultGasProvider gasProvider
  ) {
    super("", contractAddress, web3j, credentials, gasProvider);
  }

  // Método para processar uma aposta
  public TransactionReceipt placeBet(BigInteger amount) throws Exception {
    Function function = new Function(
      "placeBet", // Nome da função no contrato
      Collections.singletonList(new Uint256(amount)), // Parâmetros
      Collections.emptyList() // Tipos de retorno
    );
    return executeRemoteCallTransaction(function).send();
  }

  // Método para declarar o vencedor
  public TransactionReceipt declareWinner(String winnerAddress, BigInteger amount) throws Exception {
    Function function = new Function(
      "declareWinner", // Nome da função no contrato
      Arrays.asList(new Utf8String(winnerAddress), new Uint256(amount)), // Parâmetros
      Collections.emptyList() // Tipos de retorno
    );
    return executeRemoteCallTransaction(function).send();
  }

  // Método para obter o saldo de um jogador
  public BigInteger getBalance(String playerAddress) throws Exception {
    Function function = new Function(
      "getBalance", // Nome da função no contrato
      Collections.singletonList(new Utf8String(playerAddress)), // Parâmetros
      Collections.emptyList() // Tipos de retorno
    );
    return executeRemoteCallSingleValueReturn(function, BigInteger.class).send();
  }
}