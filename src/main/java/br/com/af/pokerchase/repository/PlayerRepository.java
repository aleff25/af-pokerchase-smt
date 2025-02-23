package br.com.af.pokerchase.repository;

import br.com.af.pokerchase.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {

  PlayerEntity findByAddress(String playerAddress);
}
