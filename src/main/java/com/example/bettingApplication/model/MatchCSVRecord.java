package com.example.bettingApplication.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchCSVRecord {

    @CsvBindByName(column = "home_team")
    private String homeTeam;

    @CsvBindByName(column = "away_team")
    private String awayTeam;

    @CsvBindByName(column = "home_win_odds")
    private Double homeWinOdds;

    @CsvBindByName(column = "away_win_odds")
    private Double awayWinOdds;

    @CsvBindByName(column = "draw_odds")
    private Double drawOdds;
}
