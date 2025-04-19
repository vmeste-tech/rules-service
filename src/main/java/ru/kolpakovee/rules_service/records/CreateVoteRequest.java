package ru.kolpakovee.rules_service.records;

import lombok.Builder;
import ru.kolpakovee.rules_service.enums.VoteStatus;

import java.util.UUID;

@Builder
public record CreateVoteRequest(
        UUID ruleId,
        VoteStatus status
) {
}
