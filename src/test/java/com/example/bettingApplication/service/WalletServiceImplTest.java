package com.example.bettingApplication.service;

import com.example.bettingApplication.entity.Wallet;
import com.example.bettingApplication.mapper.WalletMapper;
import com.example.bettingApplication.repository.WalletRepo;
import com.example.bettingApplication.model.WalletDTO;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
public class WalletServiceImplTest {
    @Mock
    private WalletRepo walletRepo;
    @Mock
    private WalletMapper walletMapper;
    @InjectMocks
    private WalletServiceImpl walletServiceImpl;

    @Test
    void getWalletById() {

        Wallet wallet = Wallet.builder()
                .id(UUID.randomUUID())
                .balance(100.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        WalletDTO walletDTO = WalletDTO.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();

        given(walletRepo.findById(wallet.getId())).willReturn(Optional.of(wallet));
        given(walletMapper.walletToWalletDto(wallet)).willReturn(walletDTO);

        Optional<WalletDTO> result = walletServiceImpl.getWalletById(wallet.getId());

        verify(walletRepo, times(1)).findById(wallet.getId());
        verify(walletMapper, times(1)).walletToWalletDto(wallet);
        assertEquals(Optional.of(walletDTO), result);
    }

    @Test
    void getWalletByIdNotFound() {

        UUID walletId = UUID.randomUUID();

        given(walletRepo.findById(walletId)).willReturn(Optional.empty());

        Optional<WalletDTO> result = walletServiceImpl.getWalletById(walletId);

        verify(walletRepo, times(1)).findById(walletId);
        assertEquals(Optional.empty(), result);
    }
}
