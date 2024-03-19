package com.example.bettingApplication.controller;

import com.example.bettingApplication.exceptions.NotFoundException;
import com.example.bettingApplication.model.MatchDTO;
import com.example.bettingApplication.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MatchController {

    public static final String MATCH_PATH = "/api/v1/match";
    public static final String MATCH_PATH_ID = MATCH_PATH + "/{matchId}";

    private final MatchService matchService;

    @GetMapping(MATCH_PATH)
    public List<MatchDTO> listMatches(){
        return matchService.listMatches();
    }

    @GetMapping(MATCH_PATH_ID)
    public MatchDTO getMatchById(@PathVariable("matchId") UUID matchId){
        return matchService.getMatchById(matchId).orElseThrow(NotFoundException::new);
    }
}
