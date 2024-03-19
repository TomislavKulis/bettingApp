package com.example.bettingApplication.controller;

import com.example.bettingApplication.enums.Outcomes;
import com.example.bettingApplication.model.BetDTO;
import com.example.bettingApplication.model.BetSlipDTO;
import com.example.bettingApplication.service.BetSlipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BetSlipController.class)
public class BetSlipControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BetSlipService betSlipService;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Test
    void getBetSlipById() throws Exception {

        BetSlipDTO betSlip = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .build();

        given(betSlipService.getBetSlipById(any(UUID.class))).willReturn(Optional.of(betSlip));

        mockMvc.perform(get(BetSlipController.BETSLIP_PATH_ID, betSlip.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(betSlip.getId().toString())))
                .andExpect(jsonPath("$.betSlipOdds", is(betSlip.getBetSlipOdds())))
                .andExpect(jsonPath("$.stake", is(betSlip.getStake())))
                .andExpect(jsonPath("$.possibleWinnings", is(betSlip.getPossibleWinnings())));

        verify(betSlipService, times(1)).getBetSlipById(uuidArgumentCaptor.capture());

        assertThat(betSlip.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getBetSlipByIdNotFound() throws Exception {

        given(betSlipService.getBetSlipById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BetSlipController.BETSLIP_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listBetSlips() throws Exception {

        BetSlipDTO betSlip1 = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(200.0)
                .stake(10.0)
                .possibleWinnings(2000.0)
                .build();

        BetSlipDTO betSlip2 = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(300.0)
                .stake(10.0)
                .possibleWinnings(3000.0)
                .build();

        BetSlipDTO betSlip3 = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .build();

        List<BetSlipDTO> betSlips = Arrays.asList(betSlip1, betSlip2, betSlip3);

        given(betSlipService.listBetSlips()).willReturn(betSlips);

        mockMvc.perform(get(BetSlipController.BETSLIP_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(betSlips.get(0).getId().toString())))
                .andExpect(jsonPath("$[1].id", is(betSlips.get(1).getId().toString())))
                .andExpect(jsonPath("$[2].id", is(betSlips.get(2).getId().toString())));

        verify(betSlipService, times(1)).listBetSlips();
    }

    @Test
    void createBetSlip() throws Exception {

        BetSlipDTO betSlip = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .build();

        Double stake = 10.0;

        UUID walletId = UUID.randomUUID();

        BetDTO bet1 = BetDTO.builder()
                .matchId(UUID.randomUUID())
                .outcome(Outcomes.HOME_WIN)
                .build();

        BetDTO bet2 = BetDTO.builder()
                .matchId(UUID.randomUUID())
                .outcome(Outcomes.AWAY_WIN)
                .build();

        List<BetDTO> bets = Arrays.asList(bet1, bet2);

        given(betSlipService.createBetSlip(bets, stake, walletId)).willReturn(betSlip);

        mockMvc.perform(post(BetSlipController.BETSLIP_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bets))
                        .param("stake", stake.toString())
                        .param("walletId", walletId.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION,
                        BetSlipController.BETSLIP_PATH + "/" + betSlip.getId().toString()));

        verify(betSlipService,times(1)).createBetSlip(bets, stake, walletId);
    }
}
