package br.com.af.pokerchase.repository;

import br.com.af.pokerchase.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}