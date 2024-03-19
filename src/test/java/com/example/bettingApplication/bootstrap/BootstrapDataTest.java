package com.example.bettingApplication.bootstrap;

import com.example.bettingApplication.repository.MatchRepo;
import com.example.bettingApplication.repository.WalletRepo;
import com.example.bettingApplication.service.MatchCsvService;
import com.example.bettingApplication.service.MatchCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MatchCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    MatchRepo matchRepo;

    @Autowired
    WalletRepo walletRepo;

    @Autowired
    MatchCsvService matchCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(matchRepo, walletRepo, matchCsvService);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        assertThat(matchRepo.count()).isEqualTo(25);
        assertThat(walletRepo.count()).isEqualTo(1);
    }
}
