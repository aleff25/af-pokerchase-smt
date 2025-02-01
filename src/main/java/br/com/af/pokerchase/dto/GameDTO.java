package br.com.af.pokerchase.dto;

import br.com.af.pokerchase.domain.GameState;
import br.com.af.pokerchase.domain.GameType;

import java.util.List;

public record GameDTO(
        String gameId,
        GameType type,
        List<PlayerDTO> players,
        List<CardDTO> communityCards,
        GameState state,
        int pot,
        int currentBet
) {}