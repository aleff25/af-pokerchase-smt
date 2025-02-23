package br.com.af.pokerchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@Entity
public class TableEntity {

  @Id
  private long id;
  private BigInteger entryFee;
  private boolean isActive;
  private int maxPlayers;
  private String state;

  @OneToMany(mappedBy = "table")
  private List<PlayerEntity> players;

}
