package com.example.walletProject.service;

import com.example.walletProject.enums.OperationType;
import com.example.walletProject.model.Wallet;

import java.util.Optional;

public interface WalletService {

    Wallet getById(Long id);

    Optional<Double> getBalanceById(Long id);


    void update(Wallet wallet);

    void updateWalletBalance(Long id, OperationType operationType, double amount);
}