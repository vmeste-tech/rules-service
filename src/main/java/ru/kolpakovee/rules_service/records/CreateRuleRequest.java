package ru.kolpakovee.rules_service.records;

public record CreateRuleRequest(
        String name,
        String description,
        double penaltyAmount,
        String cronExpression,
        String timeZone
) {
}
