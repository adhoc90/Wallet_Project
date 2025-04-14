package com.example.walletProject.model;

import com.example.walletProject.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransaction {

    private OperationType operationType;
}
