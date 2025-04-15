package com.example.walletProject.controller;

import com.example.walletProject.model.WalletTransaction;
import com.example.walletProject.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;


    @PostMapping("/wallet")
    public ResponseEntity<Void> updateWallet(@RequestBody WalletTransaction transaction) {
        walletService.updateWalletBalance(transaction.getWalletId(),
                transaction.getOperationType(),
                transaction.getAmount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<Double> getWalletBalance(@PathVariable UUID walletId) {
        double balance = walletService.getWalletBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}