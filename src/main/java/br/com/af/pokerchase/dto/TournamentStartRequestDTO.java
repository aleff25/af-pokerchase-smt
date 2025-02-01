package br.com.af.pokerchase.dto;

import java.util.List;

public record TournamentStartRequestDTO(String tournamentId, List<String> playerIds) {
}
