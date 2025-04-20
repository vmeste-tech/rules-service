package ru.kolpakovee.rules_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.kolpakovee.rules_service.records.CreateRuleRequest;
import ru.kolpakovee.rules_service.records.RuleDto;
import ru.kolpakovee.rules_service.records.RuleInfo;
import ru.kolpakovee.rules_service.records.UpdateRuleRequest;
import ru.kolpakovee.rules_service.services.RulesService;
import ru.kolpakovee.rules_service.utils.JwtUtils;

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
    public RuleDto createRule(@RequestBody CreateRuleRequest request, @AuthenticationPrincipal Jwt jwt) {
        return rulesService.createRule(request, JwtUtils.getUserId(jwt));
    }

    @PutMapping
    @Operation(summary = "Обновление правила",
            description = "Позволяет обновить существующее правило")
    public RuleDto updateRule(@RequestBody UpdateRuleRequest request, @AuthenticationPrincipal Jwt jwt) {
        return rulesService.updateRule(request, JwtUtils.getUserId(jwt));
    }

    @DeleteMapping("/{ruleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление правила правила",
            description = "Позволяет удалить существующее правило")
    public void deleteRule(@PathVariable UUID ruleId, @AuthenticationPrincipal Jwt jwt) {
        rulesService.deleteRule(ruleId, JwtUtils.getUserId(jwt));
    }

    @GetMapping("/by-id/{ruleId}")
    @Operation(summary = "Получение информации о правиле",
            description = "Позволяет получить информацию о правиле")
    public RuleInfo getRule(@PathVariable UUID ruleId) {
        return rulesService.getRule(ruleId);
    }
}
