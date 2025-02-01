package br.com.af.pokerchase.repository;

import br.com.af.pokerchase.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, String> {
}
