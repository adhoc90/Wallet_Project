package com.example.walletProject.service;

import com.example.walletProject.model.Wallet;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Wallet getById(Long id);

    Optional<Double> getBalanceById(Long id);


    void update(Wallet wallet);

    void updateWalletNumber(Long id, UUID number);
}