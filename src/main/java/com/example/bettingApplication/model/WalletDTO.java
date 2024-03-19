package com.example.bettingApplication.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Builder
@Data
public class WalletDTO {

    private UUID id;

    @NotNull
    private Double balance;
}
