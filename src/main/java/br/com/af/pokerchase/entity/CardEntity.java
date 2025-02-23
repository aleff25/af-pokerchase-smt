package br.com.af.pokerchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CardEntity {

  @Id
  private Long id;
  private String suit;
  private String rank;
}
