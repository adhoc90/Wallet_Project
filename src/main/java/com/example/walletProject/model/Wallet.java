package com.example.walletProject.model;

import com.example.walletProject.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Wallet {

    @Id
    private UUID walletId = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Column(nullable = false)
    private double balance;
}