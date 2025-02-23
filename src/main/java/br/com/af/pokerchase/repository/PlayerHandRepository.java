package br.com.af.pokerchase.repository;

import br.com.af.pokerchase.entity.PlayerHandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerHandRepository extends JpaRepository<PlayerHandEntity, UUID> {

}
