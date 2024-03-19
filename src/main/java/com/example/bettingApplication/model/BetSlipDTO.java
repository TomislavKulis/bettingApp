package com.example.bettingApplication.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BetSlipDTO {

    private UUID id;

    @NotNull
    private Double betSlipOdds;

    @NotNull
    private Double stake;

    @NotNull
    private Double possibleWinnings;
}
