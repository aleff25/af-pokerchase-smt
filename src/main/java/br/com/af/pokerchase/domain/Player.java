package br.com.af.pokerchase.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "players")
public class Player {
    @Id
    private String id;

    private String gameId;
    private String walletAddress;
    private int balance;
    private boolean folded;

    @ElementCollection
    private List<String> hand;
}
