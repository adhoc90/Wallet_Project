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
    private UUID walletNumber;
    private double balance;
}