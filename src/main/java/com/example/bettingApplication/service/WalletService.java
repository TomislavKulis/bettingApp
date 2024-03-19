package com.example.bettingApplication.service;

import com.example.bettingApplication.model.WalletDTO;

import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Optional<WalletDTO> getWalletById(UUID walletId);
}
