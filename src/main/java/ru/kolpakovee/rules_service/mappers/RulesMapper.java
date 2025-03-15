package ru.kolpakovee.rules_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kolpakovee.rules_service.entities.RuleEntity;
import ru.kolpakovee.rules_service.records.RuleDto;

@Mapper
public interface RulesMapper {

    RulesMapper INSTANCE = Mappers.getMapper(RulesMapper.class);


    RuleDto toDto(RuleEntity taskEntity);
}
