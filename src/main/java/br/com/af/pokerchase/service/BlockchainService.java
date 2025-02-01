package br.com.af.pokerchase.service;

import br.com.af.pokerchase.contract.PokerGameContract;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

@Service
public class BlockchainService {

  @Value("${blockchain.rpc.url}")
  private String rpcUrl;

  @Value("${contract.address}")
  private String contractAddress;

  @Value("${wallet.privateKey}")
  private String privateKey;

  private PokerGameContract contract;

  @PostConstruct
  public void init() throws Exception {
    Web3j web3j = Web3j.build(new HttpService(rpcUrl));
    Credentials credentials = Credentials.create(privateKey);
    contract = new PokerGameContract(contractAddress, web3j, credentials, new DefaultGasProvider());
  }

  public TransactionReceipt processBet(String playerAddress, BigInteger amount) throws Exception {
    return contract.placeBet(amount);
  }

  public TransactionReceipt distributePrize(String winnerAddress, BigInteger amount) throws Exception {
    return contract.declareWinner(winnerAddress, amount);
  }
}
