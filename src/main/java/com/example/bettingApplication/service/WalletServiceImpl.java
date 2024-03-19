package com.example.bettingApplication.service;

import com.example.bettingApplication.mapper.WalletMapper;
import com.example.bettingApplication.model.WalletDTO;
import com.example.bettingApplication.repository.WalletRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepo walletRepo;

    private final WalletMapper walletMapper;

    @Override
    public Optional<WalletDTO> getWalletById(UUID walletId) {
        return Optional.ofNullable(walletMapper.walletToWalletDto(
                walletRepo.findById(walletId).orElse(null)));
    }
}
