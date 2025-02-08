package br.com.af.pokerchase.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes2;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokerTournamentContract extends Contract {
  public static final String BINARY = "<INSIRA_O_BYTECODE_DO_CONTRATO_COMPILADO>";

  public static final String FUNC_STARTTOURNAMENT = "startTournament";
  public static final String FUNC_SUBMITACTION = "submitAction";
  public static final String FUNC_GETPLAYERBALANCE = "getPlayerBalance";

  public static final Event GAMESTARTED_EVENT = new Event("GameStarted",
    Arrays.<TypeReference<?>>asList(
      new TypeReference<Utf8String>() {
      },
      new TypeReference<DynamicArray<Address>>() {
      },
      new TypeReference<Uint256>() {
      },
      new TypeReference<Uint256>() {
      }
    ));

  public static final Event CARDSDEALT_EVENT = new Event("CardsDealt",
    Arrays.<TypeReference<?>>asList(
      new TypeReference<Uint256>() {
      },
      new TypeReference<DynamicArray<Bytes2>>() {
      }
    ));

  protected PokerTournamentContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  public static PokerTournamentContract load(
    String contractAddress,
    Web3j web3j,
    Credentials credentials,
    ContractGasProvider contractGasProvider
  ) {
    return new PokerTournamentContract(contractAddress, web3j, credentials, contractGasProvider);
  }

  public RemoteFunctionCall<TransactionReceipt> startTournament(
    String gameId,
    List<String> players,
    BigInteger buyIn,
    BigInteger smallBlind,
    BigInteger bigBlind
  ) {
    final Function function = new Function(
      FUNC_STARTTOURNAMENT,
      Arrays.<Type>asList(
        new Utf8String(gameId),
        new DynamicArray<Address>(
          Address.class,
          players.stream()
            .map(Address::new)
            .toList()
        ),
        new Uint256(buyIn),
        new Uint256(smallBlind),
        new Uint256(bigBlind)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> submitAction(String playerAddress, String action, BigInteger amount) {
    final Function function = new Function(
      FUNC_SUBMITACTION,
      Arrays.<Type>asList(
        new Address(playerAddress),
        new Utf8String(action),
        new Uint256(amount)),
      Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<BigInteger> getPlayerBalance(String playerAddress) {
    final Function function = new Function(FUNC_GETPLAYERBALANCE,
      Arrays.<Type>asList(new Address(playerAddress)),
      Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
      }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }
}