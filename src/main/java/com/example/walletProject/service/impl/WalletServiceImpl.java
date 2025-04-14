package com.example.walletProject.service.impl;

import com.example.walletProject.repository.WalletRepository;
import com.example.walletProject.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
}
