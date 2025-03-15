package ru.kolpakovee.rules_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kolpakovee.rules_service.records.CreateRuleRequest;
import ru.kolpakovee.rules_service.records.RuleDto;
import ru.kolpakovee.rules_service.records.UpdateRuleRequest;
import ru.kolpakovee.rules_service.services.RulesService;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
public class RulesController {

    private final RulesService rulesService;

    @GetMapping
    public List<RuleDto> getApartmentRules() {
        return rulesService.getApartmentRules();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RuleDto createRule(@RequestBody CreateRuleRequest request) {
        return rulesService.createRule(request);
    }

    @PutMapping
    public RuleDto updateRule(@RequestBody UpdateRuleRequest request) {
        return rulesService.updateRule(request);
    }

    @DeleteMapping("/{ruleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable UUID ruleId) {
        rulesService.deleteRule(ruleId);
    }
}
