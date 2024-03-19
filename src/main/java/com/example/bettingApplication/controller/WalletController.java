package com.example.bettingApplication.controller;

import com.example.bettingApplication.exceptions.NotFoundException;
import com.example.bettingApplication.model.WalletDTO;
import com.example.bettingApplication.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalletController {

    public static final String WALLET_PATH_ID = "/api/v1/wallet/{walletId}";

    private final WalletService walletService;

    @GetMapping(WALLET_PATH_ID)
    public WalletDTO getWalletById(@PathVariable("walletId") UUID walletId){
        return walletService.getWalletById(walletId).orElseThrow(NotFoundException::new);
    }
}
