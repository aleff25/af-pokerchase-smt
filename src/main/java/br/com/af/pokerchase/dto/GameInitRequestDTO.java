package br.com.af.pokerchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameInitRequestDTO {
  private String gameId;
  private List<String> players;
  private BigInteger smallBlind;
  private BigInteger bigBlind;
}
