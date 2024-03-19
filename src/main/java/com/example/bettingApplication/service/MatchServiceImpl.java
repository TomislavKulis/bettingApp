package com.example.bettingApplication.service;

import com.example.bettingApplication.mapper.MatchMapper;
import com.example.bettingApplication.model.MatchDTO;
import com.example.bettingApplication.repository.MatchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{

    private final MatchRepo matchRepo;

    private final MatchMapper matchMapper;

    @Override
    public List<MatchDTO> listMatches() {
        return matchRepo.findAll()
                .stream()
                .map(matchMapper::matchToMatchDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MatchDTO> getMatchById(UUID matchId) {
        return Optional.ofNullable(matchMapper.matchToMatchDto(
                matchRepo.findById(matchId).orElse(null)));
    }
}
