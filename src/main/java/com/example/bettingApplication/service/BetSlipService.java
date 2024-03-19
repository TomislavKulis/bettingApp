package com.example.bettingApplication.service;

import com.example.bettingApplication.model.BetDTO;
import com.example.bettingApplication.model.BetSlipDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetSlipService {

    List<BetSlipDTO> listBetSlips();
    Optional<BetSlipDTO> getBetSlipById(UUID betSlipId);
    BetSlipDTO createBetSlip(List<BetDTO> bets, Double stake, UUID walletId);
}
