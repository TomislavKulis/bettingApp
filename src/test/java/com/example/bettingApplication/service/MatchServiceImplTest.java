package com.example.bettingApplication.service;

import com.example.bettingApplication.entity.Match;
import com.example.bettingApplication.mapper.MatchMapper;
import com.example.bettingApplication.model.MatchDTO;
import com.example.bettingApplication.repository.MatchRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MatchServiceImplTest {
    @Mock
    private MatchRepo matchRepo;
    @Mock
    private MatchMapper matchMapper;
    @InjectMocks
    private MatchServiceImpl matchService;

    @Test
    void testListMatches() {

        Match match1 = Match.builder()
                .id(UUID.randomUUID())
                .homeTeam("Hosts1")
                .awayTeam("Visitors1")
                .homeWinOdds(2.00)
                .awayWinOdds(3.00)
                .drawOdds(4.00)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Match match2 = Match.builder()
                .id(UUID.randomUUID())
                .homeTeam("Hosts2")
                .awayTeam("Visitors2")
                .homeWinOdds(3.20)
                .awayWinOdds(2.40)
                .drawOdds(3.60)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<Match> matches = Arrays.asList(match1, match2);

        MatchDTO matchDTO1 = MatchDTO.builder()
                .id(match1.getId())
                .homeTeam("Hosts1")
                .awayTeam("Visitors1")
                .homeWinOdds(2.00)
                .awayWinOdds(3.00)
                .drawOdds(4.00)
                .build();

        MatchDTO matchDTO2 = MatchDTO.builder()
                .id(match2.getId())
                .homeTeam("Hosts2")
                .awayTeam("Visitors2")
                .homeWinOdds(3.20)
                .awayWinOdds(2.40)
                .drawOdds(3.60)
                .build();

        given(matchRepo.findAll()).willReturn(matches);
        given(matchMapper.matchToMatchDto(match1)).willReturn(matchDTO1);
        given(matchMapper.matchToMatchDto(match2)).willReturn(matchDTO2);

        List<MatchDTO> result = matchService.listMatches();

        verify(matchRepo, times(1)).findAll();
        verify(matchMapper, times(1)).matchToMatchDto(match1);
        verify(matchMapper, times(1)).matchToMatchDto(match2);
        assertEquals(2, result.size());
        assertEquals(matchDTO1, result.get(0));
        assertEquals(matchDTO2, result.get(1));
    }

    @Test
    void getMatchById() {

        Match match = Match.builder()
                .id(UUID.randomUUID())
                .homeTeam("Hosts1")
                .awayTeam("Visitors1")
                .homeWinOdds(2.00)
                .awayWinOdds(3.00)
                .drawOdds(4.00)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        MatchDTO matchDTO = MatchDTO.builder()
                .id(match.getId())
                .homeTeam("Hosts1")
                .awayTeam("Visitors1")
                .homeWinOdds(2.00)
                .awayWinOdds(3.00)
                .drawOdds(4.00)
                .build();

        given(matchRepo.findById(match.getId())).willReturn(Optional.of(match));
        given(matchMapper.matchToMatchDto(match)).willReturn(matchDTO);

        Optional<MatchDTO> result = matchService.getMatchById(match.getId());

        verify(matchRepo, times(1)).findById(match.getId());
        verify(matchMapper, times(1)).matchToMatchDto(match);
        assertEquals(Optional.of(matchDTO), result);
    }

    @Test
    void getMatchByIdNotFound() {

        UUID matchId = UUID.randomUUID();

        given(matchRepo.findById(matchId)).willReturn(Optional.empty());

        Optional<MatchDTO> result = matchService.getMatchById(matchId);

        // Verify interactions and assertions
        verify(matchRepo, times(1)).findById(matchId);
        assertEquals(Optional.empty(), result);
    }
}