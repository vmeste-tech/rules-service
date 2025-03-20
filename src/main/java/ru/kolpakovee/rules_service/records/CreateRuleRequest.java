package ru.kolpakovee.rules_service.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateRuleRequest(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Description must not be blank")
        String description,

        @PositiveOrZero(message = "Penalty amount must be zero or positive")
        double penaltyAmount,

        @NotBlank(message = "Cron expression must not be blank")
        String cronExpression,

        @NotBlank(message = "Time zone must not be blank")
        String timeZone
) {
}

