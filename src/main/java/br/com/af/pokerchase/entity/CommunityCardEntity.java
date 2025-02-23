package br.com.af.pokerchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class CommunityCardEntity {

  @Id
  private Long id;

  @ManyToOne
  private TableEntity table;

  @ManyToOne
  private CardEntity card;
}
