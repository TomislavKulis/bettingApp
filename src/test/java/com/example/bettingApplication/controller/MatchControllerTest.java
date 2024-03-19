package com.example.bettingApplication.controller;

import com.example.bettingApplication.model.MatchDTO;
import com.example.bettingApplication.service.MatchService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchController.class)
public class MatchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MatchService matchService;
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Test
    void getMatchById() throws Exception {

        MatchDTO match = MatchDTO.builder()
                .id(UUID.randomUUID())
                .homeTeam("Hosts")
                .awayTeam("Visitors")
                .homeWinOdds(2.0)
                .awayWinOdds(3.0)
                .drawOdds(4.0)
                .build();

        given(matchService.getMatchById(match.getId())).willReturn(Optional.of(match));

        mockMvc.perform(get(MatchController.MATCH_PATH_ID, match.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(match.getId().toString())))
                .andExpect(jsonPath("$.homeTeam", is(match.getHomeTeam())))
                .andExpect(jsonPath("$.awayTeam", is(match.getAwayTeam())))
                .andExpect(jsonPath("$.homeWinOdds", is(match.getHomeWinOdds())))
                .andExpect(jsonPath("$.awayWinOdds", is(match.getAwayWinOdds())))
                .andExpect(jsonPath("$.homeTeam", is(match.getHomeTeam())));

        verify(matchService, times(1)).getMatchById(uuidArgumentCaptor.capture());

        assertThat(match.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getMatchByIdNotFound() throws Exception {

        given(matchService.getMatchById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(MatchController.MATCH_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listMatches() throws Exception{

        MatchDTO match1 = MatchDTO.builder()
                .id(UUID.randomUUID())
                .homeTeam("Hosts1")
                .awayTeam("Visitors1")
                .homeWinOdds(2.00)
                .awayWinOdds(3.00)
                .drawOdds(4.00)
                .build();

        MatchDTO match2 = MatchDTO.builder()
                .id(UUID.randomUUID())
                .homeTeam("Hosts2")
                .awayTeam("Visitors2")
                .homeWinOdds(2.50)
                .awayWinOdds(2.80)
                .drawOdds(3.60)
                .build();

        MatchDTO match3 = MatchDTO.builder()
                .id(UUID.randomUUID())
                .homeTeam("Hosts3")
                .awayTeam("Visitors3")
                .homeWinOdds(2.60)
                .awayWinOdds(2.70)
                .drawOdds(3.40)
                .build();

        List<MatchDTO> matches = Arrays.asList(match1, match2, match3);

        given(matchService.listMatches()).willReturn(matches);

        mockMvc.perform(get(MatchController.MATCH_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(matches.get(0).getId().toString())))
                .andExpect(jsonPath("$[1].id", is(matches.get(1).getId().toString())))
                .andExpect(jsonPath("$[2].id", is(matches.get(2).getId().toString())));

        verify(matchService, times(1)).listMatches();
    }
}