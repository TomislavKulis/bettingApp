package com.example.bettingApplication.service;

import com.example.bettingApplication.entity.BetSlip;
import com.example.bettingApplication.entity.Match;
import com.example.bettingApplication.entity.Wallet;
import com.example.bettingApplication.enums.Outcomes;
import com.example.bettingApplication.exceptions.InsufficientFundsException;
import com.example.bettingApplication.mapper.BetSlipMapper;
import com.example.bettingApplication.model.BetDTO;
import com.example.bettingApplication.model.BetSlipDTO;
import com.example.bettingApplication.repository.BetSlipRepo;
import com.example.bettingApplication.repository.MatchRepo;
import com.example.bettingApplication.repository.WalletRepo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BetSlipServiceImplTest {
    @Mock
    private WalletRepo walletRepo;

    @Mock
    private MatchRepo matchRepo;

    @Mock
    private BetSlipRepo betSlipRepo;

    @Mock
    private BetSlipMapper betSlipMapper;

    @InjectMocks
    private BetSlipServiceImpl betSlipService;

    @Test
    void testListBetSlips() {

        BetSlip betSlip1 = BetSlip.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BetSlip betSlip2 = BetSlip.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(200.0)
                .stake(10.0)
                .possibleWinnings(2000.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<BetSlip> betSlips = Arrays.asList(betSlip1, betSlip2);

        BetSlipDTO betSlipDTO1 = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .build();

        BetSlipDTO betSlipDTO2 = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(200.0)
                .stake(10.0)
                .possibleWinnings(2000.0)
                .build();

        given(betSlipRepo.findAll()).willReturn(betSlips);
        given(betSlipMapper.betSlipToBetSlipDto(betSlip1)).willReturn(betSlipDTO1);
        given(betSlipMapper.betSlipToBetSlipDto(betSlip2)).willReturn(betSlipDTO2);

        List<BetSlipDTO> result = betSlipService.listBetSlips();

        verify(betSlipRepo, times(1)).findAll();
        verify(betSlipMapper, times(1)).betSlipToBetSlipDto(betSlip1);
        verify(betSlipMapper, times(1)).betSlipToBetSlipDto(betSlip2);
        assertEquals(2, result.size());
        assertEquals(betSlipDTO1, result.get(0));
        assertEquals(betSlipDTO2, result.get(1));
    }

    @Test
    void getBetSlipById() {

        BetSlip betSlip = BetSlip.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BetSlipDTO betSlipDTO = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .build();

        given(betSlipRepo.findById(betSlip.getId())).willReturn(Optional.of(betSlip));
        given(betSlipMapper.betSlipToBetSlipDto(betSlip)).willReturn(betSlipDTO);

        Optional<BetSlipDTO> result = betSlipService.getBetSlipById(betSlip.getId());

        verify(betSlipRepo, times(1)).findById(betSlip.getId());
        verify(betSlipMapper, times(1)).betSlipToBetSlipDto(betSlip);
        assertEquals(Optional.of(betSlipDTO), result);
    }

    @Test
    void getBetSlipByIdNotFound() {

        UUID betSlipId = UUID.randomUUID();

        given(betSlipRepo.findById(betSlipId)).willReturn(Optional.empty());

        Optional<BetSlipDTO> result = betSlipService.getBetSlipById(betSlipId);

        verify(betSlipRepo, times(1)).findById(betSlipId);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void createBetSlipSufficientBalance() {

        Wallet wallet = Wallet.builder()
                .id(UUID.randomUUID())
                .balance(100.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(walletRepo.findById(wallet.getId())).willReturn(Optional.of(wallet));

        UUID matchId1 = UUID.randomUUID();
        UUID matchId2 = UUID.randomUUID();

        BetDTO betDTO = BetDTO.builder()
                .matchId(matchId1)
                .outcome(Outcomes.HOME_WIN)
                .build();

        BetDTO betDTO2 = BetDTO.builder()
                .matchId(matchId2)
                .outcome(Outcomes.DRAW)
                .build();

        List<BetDTO> betDTOs = Arrays.asList(betDTO, betDTO2);

        Match match = Match.builder()
                .id(matchId1)
                .homeTeam("Hosts1")
                .awayTeam("Visitors1")
                .homeWinOdds(2.00)
                .awayWinOdds(3.00)
                .drawOdds(4.00)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .bets(new HashSet<>())
                .build();

        Match match2 = Match.builder()
                .id(matchId2)
                .homeTeam("Hosts2")
                .awayTeam("Visitors2")
                .homeWinOdds(2.00)
                .awayWinOdds(3.00)
                .drawOdds(4.00)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .bets(new HashSet<>())
                .build();

        given(matchRepo.findById(matchId1)).willReturn(Optional.of(match));
        given(matchRepo.findById(matchId2)).willReturn(Optional.of(match2));

        BetSlipDTO betSlipDTO = BetSlipDTO.builder()
                .id(UUID.randomUUID())
                .betSlipOdds(100.0)
                .stake(10.0)
                .possibleWinnings(1000.0)
                .build();

        given(betSlipMapper.betSlipToBetSlipDto(any())).willReturn(betSlipDTO);

        betSlipService.createBetSlip(betDTOs, 20.0, wallet.getId());

        ArgumentCaptor<BetSlip> argumentCaptor = ArgumentCaptor.forClass(BetSlip.class);

        verify(betSlipRepo).save(argumentCaptor.capture());

        BetSlip savedBetSlip = argumentCaptor.getValue();

        assertEquals(8.0, savedBetSlip.getBetSlipOdds());
        assertEquals(160.0, savedBetSlip.getPossibleWinnings());
    }

    @Test
    void createBetSlipInsufficientBalance() {

        BetDTO betDTO = BetDTO.builder()
                .matchId(UUID.randomUUID())
                .outcome(Outcomes.HOME_WIN)
                .build();

        List<BetDTO> bets = Collections.singletonList(betDTO);

        Wallet wallet = Wallet.builder()
                .id(UUID.randomUUID())
                .balance(5.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(walletRepo.findById(wallet.getId())).willReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class,
                () -> betSlipService.createBetSlip(bets, 10.0, wallet.getId()));
    }
}

