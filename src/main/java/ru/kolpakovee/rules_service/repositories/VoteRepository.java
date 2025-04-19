package ru.kolpakovee.rules_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolpakovee.rules_service.entities.VoteId;
import ru.kolpakovee.rules_service.entities.VoteEntity;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, VoteId> {

    List<VoteEntity> findAllById_RuleId(UUID ruleId);
}
