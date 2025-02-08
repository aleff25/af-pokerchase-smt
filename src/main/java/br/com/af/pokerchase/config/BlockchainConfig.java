package br.com.af.pokerchase.config;

import br.com.af.pokerchase.contract.PokerTournamentContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class BlockchainConfig {

  @Value("${contract.address}")
  private String contractAddress;

  @Bean
  public Web3j web3j(@Value("${blockchain.rpc.url}") String rpcUrl) {
    return Web3j.build(new HttpService(rpcUrl));
  }

  @Bean
  public Credentials credentials(@Value("${admin.wallet.private-key}") String privateKey) {
    return Credentials.create(privateKey);
  }

  @Bean
  public PokerTournamentContract pokerTournament(Web3j web3j, Credentials credentials) throws Exception {
    return PokerTournamentContract.load(
      contractAddress,
      web3j,
      credentials,
      new DefaultGasProvider()
    );
  }
}