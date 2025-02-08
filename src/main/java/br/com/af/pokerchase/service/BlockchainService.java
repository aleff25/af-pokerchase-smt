package br.com.af.pokerchase.service;

import br.com.af.pokerchase.contract.PokerTournamentContract;
import br.com.af.pokerchase.dto.ContractEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;

@Service
public class BlockchainService {

  @Value("${blockchain.rpc.url}")
  private String rpcUrl;

  @Value("${blockchain.contract.address}")
  private String contractAddress;

  @Value("${blockchain.wallet.privateKey}")
  private String privateKey;

  private Web3j web3j;
  private Credentials credentials;
  private PokerTournamentContract contract;


  @PostConstruct
  public void initializeContract() throws Exception {
    this.contract = PokerTournamentContract.load(
      contractAddress,
      web3j,
      credentials,
      new DefaultGasProvider() {
        @Override
        public BigInteger getGasPrice() {
          return Convert.toWei("20", Convert.Unit.GWEI).toBigInteger();
        }

        @Override
        public BigInteger getGasLimit() {
          return BigInteger.valueOf(1_500_000L);
        }
      }
    );
  }

  public void listenToGameEvents(Consumer<ContractEvent> eventHandler) {
    // Listener para GameStarted
    contract.gameStartedEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
      .subscribe(event -> {
        eventHandler.accept(new ContractEvent(
          ContractEvent.EventType.GAME_STARTED,
          event.gameId,
          event.players,
          null,
          null
        ));
      });

    // Listener para CardsDealt
    contract.cardsDealtEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
      .subscribe(event -> {
        eventHandler.accept(new ContractEvent(
          EventType.CARDS_DEALT,
          event.gameId,
          null,
          event.cards,
          null
        ));
      });
  }

  public TransactionReceipt startTournamentOnChain(
    String gameId,
    List<String> players,
    GameConfig config
  ) throws Exception {
    return contract.startTournament(
      gameId,
      players,
      config.getBuyIn(),
      config.getSmallBlind(),
      config.getBigBlind()
    ).send();
  }

  public TransactionReceipt submitActionToChain(
    String gameId,
    String playerAddress,
    String action,
    BigInteger amount
  ) throws Exception {
    return contract.submitAction(
      playerAddress,
      action,
      amount
    ).send();
  }

}
