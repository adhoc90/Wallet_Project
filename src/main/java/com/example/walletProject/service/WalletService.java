package com.example.walletProject.service;

import com.example.walletProject.model.Wallet;

import java.util.UUID;

public interface WalletService {

    Wallet getBalanceById(Long id, double balance);

    Wallet create(Wallet wallet);

    void setWalletNumber(Long id, UUID number);
}
