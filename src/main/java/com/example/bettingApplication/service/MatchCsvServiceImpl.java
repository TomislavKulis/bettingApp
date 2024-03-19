package com.example.bettingApplication.service;

import com.example.bettingApplication.model.MatchCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class MatchCsvServiceImpl implements MatchCsvService {
    @Override
    public List<MatchCSVRecord> convertCSV(File csvFile) {

        try {
            List<MatchCSVRecord> matchCSVRecords = new CsvToBeanBuilder<MatchCSVRecord>(new FileReader(csvFile))
                    .withType(MatchCSVRecord.class)
                    .build().parse();
            return matchCSVRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}