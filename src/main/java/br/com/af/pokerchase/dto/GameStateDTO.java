package br.com.af.pokerchase.dto;

import br.com.af.pokerchase.domain.GameState;

import java.util.List;

public record GameStateDTO(String gameId, GameState gameState, int gamePot, List<CardDTO> communityCards) {
}
