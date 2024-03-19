package com.example.bettingApplication.service;

import com.example.bettingApplication.model.MatchCSVRecord;

import java.io.File;
import java.util.List;

public interface MatchCsvService {
    List<MatchCSVRecord> convertCSV(File csvFile);
}
