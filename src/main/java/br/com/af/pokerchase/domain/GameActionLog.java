package br.com.af.pokerchase.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "game_actions")
@Data
public class GameActionLog {
    @Id
    @GeneratedValue
    private UUID id;

    private String gameId;
    private String playerId;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String details;
    private Instant timestamp;
}


