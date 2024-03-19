package com.example.bettingApplication.model;

import com.example.bettingApplication.enums.Outcomes;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BetDTO {

    @NotNull
    private UUID matchId;

    @NotNull
    private Outcomes outcome;
}
