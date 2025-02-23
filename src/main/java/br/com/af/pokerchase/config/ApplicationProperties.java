package br.com.af.pokerchase.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
public class ApplicationProperties {

  @Value("${blockchain.rpc.url}")
  private String rpcUrl;

  @Value("${blockchain.contract.address}")
  private String contractAddress;

  @Value("${blockchain.wallet.privateKey}")
  private String privateKey;
}
