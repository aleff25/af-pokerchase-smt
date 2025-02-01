package br.com.af.pokerchase.dto;

import br.com.af.pokerchase.domain.GameState;

import java.util.List;

public record GameStateResponseDTO(
        String gameId,
        GameState state,
        int pot,
        List<CardDTO> communityCards
) {}
