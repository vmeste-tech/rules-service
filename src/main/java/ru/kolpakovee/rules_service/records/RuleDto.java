package ru.kolpakovee.rules_service.records;


import ru.kolpakovee.rules_service.enums.RuleStatus;

import java.util.UUID;

public record RuleDto(
        UUID id,
        String name,
        String description,
        RuleStatus status,
        double penaltyAmount,
        String cronExpression,
        String timeZone,
        boolean autoCreateTasks
) {
}
