package com.example.walletProject.service.impl;

import com.example.walletProject.exception.WalletNotFoundException;
import com.example.walletProject.model.Wallet;
import com.example.walletProject.repository.WalletRepository;
import com.example.walletProject.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public void updateWalletNumber(Long id, UUID number) {
        Wallet wallet = getById(id);
        wallet.setWalletNumber(number);
        update(wallet);
    }
}