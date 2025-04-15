package com.example.walletProject.model;

import com.example.walletProject.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WalletTransaction {

    private UUID walletId;
    private OperationType operationType;
    private double amount;
}