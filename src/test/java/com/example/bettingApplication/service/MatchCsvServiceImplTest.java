package com.example.bettingApplication.service;

import com.example.bettingApplication.model.MatchCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MatchCsvServiceImplTest {

    MatchCsvService matchCsvService = new MatchCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:csvdata/dummyoffer.csv");

        List<MatchCSVRecord> recs = matchCsvService.convertCSV(file);

        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);
    }
}