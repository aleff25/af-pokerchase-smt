package br.com.af.pokerchase.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Entity
public class Game {

  @Id
  private String id;

  @Enumerated(EnumType.STRING)
  private GameType type; // Texas Hold'em, Omaha, etc.

  @OneToMany(cascade = CascadeType.ALL)
  private List<Player> players; // Jogadores na mesa

  @ElementCollection
  private List<String> communityCards; // Cartas comunitárias (Flop, Turn, River)

  @ElementCollection
  private List<String> deckCards; // Cartas restantes no baralho

  @Enumerated(EnumType.STRING)
  private GameState state; // Estado atual do jogo (ex: PREFLOP, FLOP)

  private int pot; // Valor total do pote
  private int currentBet; // Aposta atual

  private Instant createdAt; // Data de criação

  // Remove e retorna a próxima carta do topo do baralho
  public String draw() {
    if (deckCards.isEmpty()) {
      throw new IllegalStateException("O baralho está vazio");
    }
    return deckCards.remove(0);
  }
}
