package ru.kolpakovee.rules_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kolpakovee.rules_service.enums.RuleStatus;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "rules")
public class RuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    private UUID apartmentId;

    @Column(length = 100)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private RuleStatus status;

    private double penaltyAmount;

    private String cronExpression;

    @Column(length = 50)
    private String timeZone;
}
