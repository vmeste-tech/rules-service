package ru.kolpakovee.rules_service.records;

import lombok.Builder;
import ru.kolpakovee.rules_service.enums.RuleStatus;

import java.util.UUID;

@Builder
public record ChangeStatusRequest(
        UUID ruleId,
        RuleStatus status
) {
}
