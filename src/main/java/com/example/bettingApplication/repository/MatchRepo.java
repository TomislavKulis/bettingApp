package com.example.bettingApplication.repository;

import com.example.bettingApplication.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MatchRepo extends JpaRepository<Match, UUID> {
}
