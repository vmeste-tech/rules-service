package ru.kolpakovee.rules_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolpakovee.rules_service.entities.RuleEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface RulesRepository extends JpaRepository<RuleEntity, UUID> {

    List<RuleEntity> findAllByApartmentId(UUID apartmentId);
}
