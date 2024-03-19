package com.example.bettingApplication.repository;

import com.example.bettingApplication.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepo extends JpaRepository<Wallet, UUID> {
}
