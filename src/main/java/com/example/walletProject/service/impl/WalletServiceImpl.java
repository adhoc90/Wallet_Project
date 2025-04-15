package com.example.walletProject.service.impl;

import com.example.walletProject.enums.OperationType;
import com.example.walletProject.exception.InsufficientFundsException;
import com.example.walletProject.exception.WalletNotFoundException;
import com.example.walletProject.model.Wallet;
import com.example.walletProject.repository.WalletRepository;
import com.example.walletProject.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet getById(Long id) {
        return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }

    @Override
    public Optional<Double> getBalanceById(Long id) {
        return walletRepository.findBalanceById(id);
    }


    @Override
    public void update(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public void updateWalletBalance(Long id, OperationType operationType, double amount) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() ->
                new WalletNotFoundException("Wallet with ID " + id + " not found"));

        switch (operationType) {
            case DEPOSIT -> wallet.setBalance(wallet.getBalance() + amount);
            case WITHDRAW -> {
                if (wallet.getBalance() < amount) {
                    throw new InsufficientFundsException("Insufficient funds in wallet " + id);
                }
                wallet.setBalance(wallet.getBalance() - amount);
            }
        }
        walletRepository.save(wallet);
    }
}