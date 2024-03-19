package com.example.bettingApplication.service;

import com.example.bettingApplication.model.MatchDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchService {

    List<MatchDTO> listMatches();
    Optional<MatchDTO> getMatchById(UUID matchId);
}
