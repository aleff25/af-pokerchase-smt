package br.com.af.pokerchase.repository;

import br.com.af.pokerchase.entity.CommunityCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommunityCardRepository extends JpaRepository<CommunityCardEntity, UUID> {

}
