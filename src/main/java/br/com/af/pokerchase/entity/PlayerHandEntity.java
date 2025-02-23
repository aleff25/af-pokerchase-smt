package br.com.af.pokerchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PlayerHandEntity {

  @Id
  private Long id;

  @ManyToOne
  private PlayerEntity player;

  @ManyToOne
  private CardEntity card1;

  @ManyToOne
  private CardEntity card2;

}
