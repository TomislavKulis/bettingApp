package com.example.bettingApplication.repository;

import com.example.bettingApplication.entity.BetSlip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BetSlipRepo extends JpaRepository<BetSlip, UUID> {
}
