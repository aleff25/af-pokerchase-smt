package br.com.af.pokerchase.entity;

import br.com.af.pokerchase.enums.PlayerActionEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigInteger;

@Data
@Entity
public class ActionEntity {

  @Id
  private Long id;
  @Enumerated
  private PlayerActionEnum action;
  private BigInteger amount;

  @ManyToOne
  private PlayerEntity player;

  @ManyToOne
  private TableEntity table;

}
