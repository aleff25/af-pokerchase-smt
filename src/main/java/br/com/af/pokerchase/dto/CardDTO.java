package br.com.af.pokerchase.dto;

import br.com.af.pokerchase.domain.Rank;
import br.com.af.pokerchase.domain.Suit;

public record CardDTO(
  Suit suit,
  Rank rank
) {
}
