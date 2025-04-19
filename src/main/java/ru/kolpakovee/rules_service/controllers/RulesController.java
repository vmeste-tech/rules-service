package ru.kolpakovee.rules_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kolpakovee.rules_service.records.CreateRuleRequest;
import ru.kolpakovee.rules_service.records.RuleDto;
import ru.kolpakovee.rules_service.records.RuleInfo;
import ru.kolpakovee.rules_service.records.UpdateRuleRequest;
import ru.kolpakovee.rules_service.services.RulesService;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Управление правилами", description = "API для управления правилами")
public class RulesController {

    private final RulesService rulesService;

    @GetMapping("/{apartmentId}")
    @Operation(summary = "Получение правил по идентификатору квартиры",
            description = "Позволяет получить правила по идентификатору квартиры")
    public List<RuleDto> getApartmentRules(@PathVariable UUID apartmentId) {
        return rulesService.getApartmentRules(apartmentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание правила",
            description = "Позволяет создать новое правило")
    public RuleDto createRule(@RequestBody CreateRuleRequest request) {
        return rulesService.createRule(request);
    }

    @PutMapping
    @Operation(summary = "Обновление правила",
            description = "Позволяет обновить существующее правило")
    public RuleDto updateRule(@RequestBody UpdateRuleRequest request) {
        return rulesService.updateRule(request);
    }

    @DeleteMapping("/{ruleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление правила правила",
            description = "Позволяет удалить существующее правило")
    public void deleteRule(@PathVariable UUID ruleId) {
        rulesService.deleteRule(ruleId);
    }

    @GetMapping("/by-id/{ruleId}")
    @Operation(summary = "Получение информации о правиле",
            description = "Позволяет получить информацию о правиле")
    public RuleInfo getRule(@PathVariable UUID ruleId) {
        return rulesService.getRule(ruleId);
    }
}
