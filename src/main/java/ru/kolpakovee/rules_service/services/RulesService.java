package ru.kolpakovee.rules_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolpakovee.rules_service.clients.UserServiceClient;
import ru.kolpakovee.rules_service.entities.RuleEntity;
import ru.kolpakovee.rules_service.enums.RuleStatus;
import ru.kolpakovee.rules_service.mappers.RulesMapper;
import ru.kolpakovee.rules_service.records.CreateRuleRequest;
import ru.kolpakovee.rules_service.records.RuleDto;
import ru.kolpakovee.rules_service.records.UpdateRuleRequest;
import ru.kolpakovee.rules_service.repositories.RulesRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RulesService {

    private final RulesRepository rulesRepository;
    private final UserServiceClient userServiceClient;

    public List<RuleDto> getApartmentRules(UUID apartmentId) {
        return rulesRepository
                .findAllByApartmentId(apartmentId)
                .stream()
                .map(RulesMapper.INSTANCE::toDto)
                .toList();
    }

    public RuleDto createRule(CreateRuleRequest request) {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setName(request.name());
        ruleEntity.setDescription(request.description());
        ruleEntity.setPenaltyAmount(request.penaltyAmount());
        ruleEntity.setStatus(RuleStatus.VOTING);
        ruleEntity.setApartmentId(userServiceClient.getApartmentByToken().apartmentId());
        ruleEntity.setCronExpression(request.cronExpression());
        ruleEntity.setTimeZone(request.timeZone());

        return RulesMapper.INSTANCE.toDto(rulesRepository.save(ruleEntity));
    }

    public RuleDto updateRule(UpdateRuleRequest request) {
        RuleEntity ruleEntity = rulesRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        ruleEntity.setName(request.name());
        ruleEntity.setDescription(request.description());
        ruleEntity.setPenaltyAmount(request.penaltyAmount());
        ruleEntity.setStatus(request.status());
        ruleEntity.setCronExpression(request.cronExpression());
        ruleEntity.setTimeZone(request.timeZone());
        return RulesMapper.INSTANCE.toDto(rulesRepository.save(ruleEntity));
    }

    public void deleteRule(UUID ruleId) {
        rulesRepository.deleteById(ruleId);
    }
}
