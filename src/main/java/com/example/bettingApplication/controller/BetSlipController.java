package com.example.bettingApplication.controller;

import com.example.bettingApplication.exceptions.InsufficientFundsException;
import com.example.bettingApplication.exceptions.NotFoundException;
import com.example.bettingApplication.model.BetDTO;
import com.example.bettingApplication.model.BetSlipDTO;
import com.example.bettingApplication.service.BetSlipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BetSlipController {

    public static final String BETSLIP_PATH = "/api/v1/betSlip";
    public static final String BETSLIP_PATH_ID = BETSLIP_PATH + "/{betSlipId}";

    private final BetSlipService betSlipService;


    @PostMapping(BETSLIP_PATH)
    public ResponseEntity<?> handleBetSlipPost(@RequestBody List<BetDTO> bets,
                                               @RequestParam Double stake,
                                               @RequestParam UUID walletId) {
        try {
            BetSlipDTO createdBetSlip = betSlipService.createBetSlip(bets, stake, walletId);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", BETSLIP_PATH + "/" + createdBetSlip.getId().toString());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Insufficient funds in the wallet. Please top up your balance.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }

    @GetMapping(BETSLIP_PATH)
    public List<BetSlipDTO> listBetSlips(){
        return betSlipService.listBetSlips();
    }

    @GetMapping(BETSLIP_PATH_ID)
    public BetSlipDTO getBetSlipById(@PathVariable("betSlipId") UUID betSlipId){
        return betSlipService.getBetSlipById(betSlipId).orElseThrow(NotFoundException::new);
    }
}

