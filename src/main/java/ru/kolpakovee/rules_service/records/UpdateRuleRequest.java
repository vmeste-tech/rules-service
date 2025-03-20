package ru.kolpakovee.rules_service.records;

import ru.kolpakovee.rules_service.enums.RuleStatus;

import java.util.UUID;

public record UpdateRuleRequest(
        UUID id,
        String name,
        String description,
        double penaltyAmount,
        String cronExpression,
        String timeZone,
        RuleStatus status
) {
}
