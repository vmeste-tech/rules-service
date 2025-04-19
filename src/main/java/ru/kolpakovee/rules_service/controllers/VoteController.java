package ru.kolpakovee.rules_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.kolpakovee.rules_service.records.CreateVoteRequest;
import ru.kolpakovee.rules_service.records.VoteDto;
import ru.kolpakovee.rules_service.services.VoteService;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Управление голосованием", description = "API для управления голосованием")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto vote(@RequestBody CreateVoteRequest voteRequest, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return voteService.vote(voteRequest, UUID.fromString(userId));
    }
}
