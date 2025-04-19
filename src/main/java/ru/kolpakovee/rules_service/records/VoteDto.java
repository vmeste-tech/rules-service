package ru.kolpakovee.rules_service.records;

import ru.kolpakovee.rules_service.enums.VoteStatus;

import java.util.UUID;

public record VoteDto(
        UUID ruleId,
        UUID userId,
        VoteStatus vote
) {
}
