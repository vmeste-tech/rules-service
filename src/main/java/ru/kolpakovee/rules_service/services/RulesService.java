package ru.kolpakovee.rules_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolpakovee.rules_service.clients.UserServiceClient;
import ru.kolpakovee.rules_service.constants.NotificationMessages;
import ru.kolpakovee.rules_service.entities.RuleEntity;
import ru.kolpakovee.rules_service.enums.RuleStatus;
import ru.kolpakovee.rules_service.mappers.RulesMapper;
import ru.kolpakovee.rules_service.mappers.VoteMapper;
import ru.kolpakovee.rules_service.producer.NotificationEventProducer;
import ru.kolpakovee.rules_service.records.*;
import ru.kolpakovee.rules_service.repositories.RulesRepository;
import ru.kolpakovee.rules_service.repositories.VoteRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RulesService {

    private final RulesRepository rulesRepository;
    private final VoteRepository voteRepository;
    private final UserServiceClient userServiceClient;

    private final NotificationEventProducer producer;

    public List<RuleDto> getApartmentRules(UUID apartmentId) {
        return rulesRepository
                .findAllByApartmentId(apartmentId)
                .stream()
                .map(RulesMapper.INSTANCE::toDto)
                .toList();
    }

    @Transactional
    public RuleDto createRule(CreateRuleRequest request, UUID userId) {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setName(request.name());
        ruleEntity.setDescription(request.description());
        ruleEntity.setPenaltyAmount(request.penaltyAmount());
        ruleEntity.setStatus(RuleStatus.VOTING);
        ruleEntity.setApartmentId(userServiceClient.getApartmentByToken().apartmentId());
        ruleEntity.setCronExpression(request.cronExpression());
        ruleEntity.setTimeZone(request.timeZone());
        ruleEntity.setAutoCreateTasks(request.autoCreateTasks());

        producer.send(userId, NotificationMessages.CREATE_RULE);

        return RulesMapper.INSTANCE.toDto(rulesRepository.save(ruleEntity));
    }

    @Transactional
    public RuleDto updateRule(UpdateRuleRequest request, UUID userId) {
        RuleEntity ruleEntity = rulesRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        ruleEntity.setName(request.name());
        ruleEntity.setDescription(request.description());
        ruleEntity.setPenaltyAmount(request.penaltyAmount());
        ruleEntity.setStatus(request.status());
        ruleEntity.setCronExpression(request.cronExpression());
        ruleEntity.setTimeZone(request.timeZone());

        producer.send(userId, NotificationMessages.UPDATE_RULE);

        return RulesMapper.INSTANCE.toDto(rulesRepository.save(ruleEntity));
    }

    @Transactional
    public void deleteRule(UUID ruleId, UUID userId) {
        producer.send(userId, NotificationMessages.DELETE_RULE);
        rulesRepository.deleteById(ruleId);
    }

    public void changeStatus(ChangeStatusRequest request) {
        RuleEntity ruleEntity = rulesRepository.findById(request.ruleId())
                .orElseThrow(() -> new RuntimeException("Rule not found"));

        ruleEntity.setStatus(request.status());
        RulesMapper.INSTANCE.toDto(rulesRepository.save(ruleEntity));
    }

    public RuleInfo getRule(UUID ruleId) {
        RuleDto rule = RulesMapper.INSTANCE.toDto(rulesRepository.findById(ruleId)
                .orElseThrow(() -> new RuntimeException("Rule not found")));

        List<VoteDto> voteResult = voteRepository.findAllById_RuleId(ruleId).stream()
                .map(VoteMapper.INSTANCE::toDto)
                .toList();

        return RuleInfo.builder()
                .id(rule.id())
                .name(rule.name())
                .description(rule.description())
                .penaltyAmount(rule.penaltyAmount())
                .status(rule.status())
                .cronExpression(rule.cronExpression())
                .voteResult(voteResult)
                .build();
    }
}
