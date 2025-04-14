package com.example.walletProject.service.impl;

import com.example.walletProject.model.Wallet;
import com.example.walletProject.repository.WalletRepository;
import com.example.walletProject.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet getBalanceById(Long id, double balance) {
        return null;
    }

    @Override
    public Wallet create(Wallet wallet) {
        return null;
    }

    @Override
    public void setWalletNumber(Long id, UUID number) {

    }
}
