package com.example.bettingApplication.bootstrap;

import com.example.bettingApplication.entity.Match;
import com.example.bettingApplication.entity.Wallet;
import com.example.bettingApplication.model.MatchCSVRecord;
import com.example.bettingApplication.repository.MatchRepo;
import com.example.bettingApplication.repository.WalletRepo;
import com.example.bettingApplication.service.MatchCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final MatchRepo matchRepo;

    private final WalletRepo walletRepo;

    private final MatchCsvService matchCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
            loadWalletData();
            loadMatchData();
    }

    private void loadMatchData() throws FileNotFoundException {
        if (matchRepo.count() == 0){
            File file = ResourceUtils.getFile("classpath:csvdata/dummyoffer.csv");

            List<MatchCSVRecord> recs = matchCsvService.convertCSV(file);

            recs.forEach(matchCSVRecord -> matchRepo.save(Match.builder()
                    .homeTeam(matchCSVRecord.getHomeTeam())
                    .awayTeam(matchCSVRecord.getAwayTeam())
                    .homeWinOdds(matchCSVRecord.getHomeWinOdds())
                    .awayWinOdds(matchCSVRecord.getAwayWinOdds())
                    .drawOdds(matchCSVRecord.getDrawOdds())
                    .build()));
        }
    }
    private void loadWalletData(){
        if(walletRepo.count() == 0){

            Wallet wallet = Wallet.builder()
                    .balance(100.0)
                    .build();

            walletRepo.save(wallet);
        }
    }
}