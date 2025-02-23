package br.com.af.pokerchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@Entity
public class PlayerEntity {

  @Id
  private Long id;
  private String name;
  private String address;
  private BigInteger balance;

  @ManyToOne
  private TableEntity table;

  @OneToMany(mappedBy = "player")
  private List<PlayerHandEntity> hands;

  @OneToMany(mappedBy = "player")
  private List<ActionEntity> actions;
}
