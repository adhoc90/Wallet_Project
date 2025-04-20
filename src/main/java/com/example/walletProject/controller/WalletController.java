package com.example.walletProject.controller;

import com.example.walletProject.model.Wallet;
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


    @PutMapping("/wallet")
    public ResponseEntity<Void> updateWallet(@RequestBody Wallet wallet) {
        walletService.updateWalletBalance(wallet.getWalletId(),
                wallet.getOperationType(),
                wallet.getBalance());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<Double> getWalletBalance(@PathVariable UUID walletId) {
        double balance = walletService.getWalletBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping
    public ResponseEntity<Wallet> create(@RequestBody Wallet wallet) {
        return ResponseEntity.ok(walletService.createWallet(wallet));
    }
}