package br.com.af.pokerchase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public ApplicationProperties applicationProperties() {
    return new ApplicationProperties();
  }
}
