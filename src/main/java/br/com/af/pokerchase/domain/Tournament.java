package br.com.af.pokerchase.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tournaments")
public class Tournament {
  @Id
  private String id;

  @ElementCollection
  private List<String> playerIds;

  @ElementCollection
  private List<String> activeGames;

  private int currentRound;
  private TournamentStatus status;
}
