package ru.kolpakovee.rules_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class VoteId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "rule_id")
    private UUID ruleId;
}
