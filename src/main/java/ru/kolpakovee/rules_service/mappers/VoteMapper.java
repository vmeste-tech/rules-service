package ru.kolpakovee.rules_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.kolpakovee.rules_service.entities.VoteEntity;
import ru.kolpakovee.rules_service.records.VoteDto;

@Mapper
public interface VoteMapper {

    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    @Mapping(source = "id.userId", target = "userId")
    @Mapping(source = "id.ruleId", target = "ruleId")
    VoteDto toDto(VoteEntity entity);
}
