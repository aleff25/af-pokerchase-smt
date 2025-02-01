package br.com.af.pokerchase.repository;

import br.com.af.pokerchase.domain.GameActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameActionLogRepository extends JpaRepository<GameActionLog, UUID> {
}
