package ru.kolpakovee.rules_service.records;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ApartmentInfo(
        UUID apartmentId,
        String name,
        String address,
        List<UserInfoDto> users
) {
}
