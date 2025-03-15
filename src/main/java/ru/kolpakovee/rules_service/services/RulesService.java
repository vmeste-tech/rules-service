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

    public List<RuleDto> getApartmentRules() {
        return rulesRepository
                .findAllByApartmentId(userServiceClient.getApartmentByToken().apartmentId())
                .stream()
                .map(RulesMapper.INSTANCE::toDto)
                .toList();
    }

    public RuleDto createRule(CreateRuleRequest request) {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setDescription(request.description());
        ruleEntity.setName(request.name());
        ruleEntity.setPenaltyAmount(request.penaltyAmount());
        ruleEntity.setStatus(RuleStatus.voting);
        ruleEntity.setApartmentId(userServiceClient.getApartmentByToken().apartmentId());

        return RulesMapper.INSTANCE.toDto(
                rulesRepository.save(ruleEntity)
        );
    }

    public RuleDto updateRule(UpdateRuleRequest request) {
        throw new UnsupportedOperationException();
    }

    public void deleteRule(UUID ruleId) {
        throw new UnsupportedOperationException();
    }
}
