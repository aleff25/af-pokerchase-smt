package br.com.af.pokerchase.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Deck {
  private List<String> cards;

  public Deck() {
    initialize();
    shuffle();
  }

  // Inicializa o baralho com todas as 52 cartas
  private void initialize() {
    cards = new ArrayList<>();
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        cards.add(suit.name() + "_" + rank.name());
      }
    }
  }

  // Embaralha as cartas
  public void shuffle() {
    Collections.shuffle(cards);
  }

  public String drawCard() {
    if (cards.isEmpty()) {
      throw new IllegalStateException("O baralho está vazio");
    }
    return cards.remove(0);
  }
}