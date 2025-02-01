package br.com.af.pokerchase.dto;

import java.util.List;

public record PlayerDTO(
        String playerId,
        String name,
        int balance,
        List<CardDTO> hand,
        boolean folded
) {}
