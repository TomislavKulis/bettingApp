package com.example.bettingApplication.controller;

import com.example.bettingApplication.model.WalletDTO;
import com.example.bettingApplication.service.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WalletService walletService;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Test
    void getWalletById() throws Exception {

        WalletDTO wallet = WalletDTO.builder()
                .id(UUID.randomUUID())
                .balance(200.0)
                .build();

        given(walletService.getWalletById(any(UUID.class))).willReturn(Optional.of(wallet));

        mockMvc.perform(get(WalletController.WALLET_PATH_ID, wallet.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance", is(wallet.getBalance())))
                .andExpect(jsonPath("$.id", is(wallet.getId().toString())));

        verify(walletService, times(1)).getWalletById(uuidArgumentCaptor.capture());

        assertThat(wallet.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getWalletByIdNotFound() throws Exception{

        given(walletService.getWalletById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(WalletController.WALLET_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
