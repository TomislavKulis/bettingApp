package com.example.bettingApplication.service;

import com.example.bettingApplication.exceptions.InsufficientFundsException;
import com.example.bettingApplication.exceptions.NotFoundException;
import com.example.bettingApplication.entity.Bet;
import com.example.bettingApplication.entity.BetSlip;
import com.example.bettingApplication.entity.Match;
import com.example.bettingApplication.entity.Wallet;
import com.example.bettingApplication.mapper.BetSlipMapper;
import com.example.bettingApplication.model.BetDTO;
import com.example.bettingApplication.model.BetSlipDTO;
import com.example.bettingApplication.repository.BetSlipRepo;
import com.example.bettingApplication.repository.MatchRepo;
import com.example.bettingApplication.repository.WalletRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BetSlipServiceImpl implements BetSlipService{

    private final BetSlipRepo betSlipRepo;

    private final BetSlipMapper betSlipMapper;

    private final MatchRepo matchRepo;

    private final WalletRepo walletRepo;

    @Override
    public List<BetSlipDTO> listBetSlips() {
        return betSlipRepo.findAll()
                .stream()
                .map(betSlipMapper::betSlipToBetSlipDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BetSlipDTO> getBetSlipById(UUID id) {
        return Optional.ofNullable(betSlipMapper.betSlipToBetSlipDto(
                betSlipRepo.findById(id).orElse(null)));
    }

    @Override
    @Transactional
    public BetSlipDTO createBetSlip(List<BetDTO> bets, Double stake, UUID walletId) {

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        if (wallet.getBalance() < stake) {
            throw new InsufficientFundsException("Insufficient funds in the wallet");
        }

        wallet.reduceBalance(stake);
        walletRepo.save(wallet);

        BetSlip betSlip = BetSlip.builder()
                .stake(stake)
                .betSlipOdds(calcTotalOdds(bets))
                .possibleWinnings(calcTotalOdds(bets) * stake)
                .bets(new HashSet<>())
                .build();

        for (BetDTO betDTO : bets) {

            Bet bet = Bet.builder()
                    .match(matchRepo.findById(betDTO.getMatchId())
                            .orElseThrow(() -> new EntityNotFoundException("Match not found")))
                    .betSlip(betSlip)
                    .outcome(betDTO.getOutcome())
                    .build();

            bet.setBetSlip(betSlip);

        }

        return betSlipMapper.betSlipToBetSlipDto(betSlipRepo.save(betSlip));
    }

    public double calcTotalOdds(List<BetDTO> bets) {

        double totalOdds = 1.00;

        for (BetDTO bet : bets) {
            Match match = matchRepo.findById(bet.getMatchId()).orElse(null);

            if (match != null) {
                double odds = switch (bet.getOutcome()) {
                    case HOME_WIN -> match.getHomeWinOdds();
                    case AWAY_WIN -> match.getAwayWinOdds();
                    default -> match.getDrawOdds();
                };

                totalOdds *= odds;
            }
        }
        return totalOdds;
    }
}