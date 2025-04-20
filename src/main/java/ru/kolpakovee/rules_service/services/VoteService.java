package ru.kolpakovee.rules_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolpakovee.rules_service.clients.UserServiceClient;
import ru.kolpakovee.rules_service.constants.NotificationMessages;
import ru.kolpakovee.rules_service.entities.VoteEntity;
import ru.kolpakovee.rules_service.entities.VoteId;
import ru.kolpakovee.rules_service.enums.RuleStatus;
import ru.kolpakovee.rules_service.enums.VoteStatus;
import ru.kolpakovee.rules_service.mappers.VoteMapper;
import ru.kolpakovee.rules_service.producer.NotificationEventProducer;
import ru.kolpakovee.rules_service.records.ApartmentInfo;
import ru.kolpakovee.rules_service.records.ChangeStatusRequest;
import ru.kolpakovee.rules_service.records.CreateVoteRequest;
import ru.kolpakovee.rules_service.records.VoteDto;
import ru.kolpakovee.rules_service.repositories.VoteRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final UserServiceClient userServiceClient;
    private final VoteRepository voteRepository;
    private final RulesService rulesService;

    private final NotificationEventProducer producer;

    @Transactional
    public VoteDto vote(CreateVoteRequest voteRequest, UUID userId) {
        producer.send(userId, NotificationMessages.VOTE);

        if (voteRequest.status() == VoteStatus.AGAINST) {
            rulesService.changeStatus(ChangeStatusRequest.builder()
                    .ruleId(voteRequest.ruleId())
                    .status(RuleStatus.REJECTED)
                    .build());
            return createVoteEntity(userId, voteRequest.ruleId(), VoteStatus.AGAINST);
        }

        ApartmentInfo apartmentInfo = userServiceClient.getApartmentByToken();
        List<VoteEntity> votes = voteRepository.findAllById_RuleId(voteRequest.ruleId());

        if (apartmentInfo.users().size() == votes.size() + 1) {
            rulesService.changeStatus(ChangeStatusRequest.builder()
                    .ruleId(voteRequest.ruleId())
                    .status(RuleStatus.ACCEPTED)
                    .build());
        }

        return createVoteEntity(userId, voteRequest.ruleId(), VoteStatus.FOR);
    }

    private VoteDto createVoteEntity(UUID userId, UUID ruleId, VoteStatus status) {
        VoteEntity voteEntity = new VoteEntity();

        VoteId voteId = new VoteId();
        voteId.setUserId(userId);
        voteId.setRuleId(ruleId);

        voteEntity.setId(voteId);
        voteEntity.setVote(status);

        return VoteMapper.INSTANCE.toDto(voteRepository.save(voteEntity));
    }
}
