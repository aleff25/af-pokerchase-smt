package br.com.af.pokerchase;

import org.springframework.boot.SpringApplication;

public class TestPokerChaseApplication {

	public static void main(String[] args) {
		SpringApplication.from(PokerChaseApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
