package com.example.walletProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Wallet {

    @Id
    private Long id;

    private UUID walletNumber;

    private double balance;

    public Wallet(Long id, UUID walletNumber, double balance) {
        this.id = id;
        this.walletNumber = walletNumber;
        this.balance = balance;
    }
}