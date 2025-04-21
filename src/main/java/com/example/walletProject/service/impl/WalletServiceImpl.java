package com.example.walletProject.service.impl;

import com.example.walletProject.enums.OperationType;
import com.example.walletProject.exception.InsufficientFundsException;
import com.example.walletProject.exception.WalletNotFoundException;
import com.example.walletProject.model.Wallet;
import com.example.walletProject.repository.WalletRepository;
import com.example.walletProject.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Double getWalletBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() ->
                new WalletNotFoundException("Wallet with ID " + walletId + " not found"));
        return wallet.getBalance();
    }


    @Override
    public void updateWalletBalance(UUID walletId, OperationType operationType, double amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() ->
                new WalletNotFoundException("Wallet with ID " + walletId + " not found"));

        switch (operationType) {
            case DEPOSIT -> wallet.setBalance(wallet.getBalance() + amount);
            case WITHDRAW -> {
                if (wallet.getBalance() < amount) {
                    throw new InsufficientFundsException("Insufficient funds in wallet " + walletId);
                }
                wallet.setBalance(wallet.getBalance() - amount);
            }
        }
        walletRepository.save(wallet);
    }

    @Override
    public Wallet createWallet(Wallet wallet) {
        log.info("запрос на создание кошелька");
        return walletRepository.save(wallet);
    }
}