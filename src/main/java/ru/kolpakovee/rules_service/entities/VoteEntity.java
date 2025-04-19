package ru.kolpakovee.rules_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kolpakovee.rules_service.enums.VoteStatus;

@Getter
@Setter
@Entity
@Table(name = "votes")
public class VoteEntity {

    @EmbeddedId
    private VoteId id;

    @Enumerated(EnumType.STRING)
    private VoteStatus vote;
}
