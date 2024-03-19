package com.example.bettingApplication.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Builder
@Data
public class MatchDTO {

    private UUID id;

    @NotNull
    @NotBlank
    private String homeTeam;

    @NotNull
    @NotBlank
    private String awayTeam;

    @NotNull
    private Double homeWinOdds;

    @NotNull
    private Double awayWinOdds;

    @NotNull
    private Double drawOdds;
}
