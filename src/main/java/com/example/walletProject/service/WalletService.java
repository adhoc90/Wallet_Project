package com.example.walletProject.service;

import com.example.walletProject.enums.OperationType;

import java.util.UUID;

public interface WalletService {

    Double getWalletBalance(UUID walletId);

    void updateWalletBalance(UUID walletId, OperationType operationType, double amount);
}