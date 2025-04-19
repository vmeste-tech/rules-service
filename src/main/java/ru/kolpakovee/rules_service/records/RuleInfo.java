package ru.kolpakovee.rules_service.records;

import lombok.Builder;
import ru.kolpakovee.rules_service.enums.RuleStatus;

import java.util.List;
import java.util.UUID;

@Builder
public record RuleInfo(
        UUID id,
        String name,
        String description,
        RuleStatus status,
        double penaltyAmount,
        String cronExpression,
        List<VoteDto> voteResult
) {
}
