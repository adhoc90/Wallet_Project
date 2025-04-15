package com.example.walletProject.repository;

import com.example.walletProject.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("SELECT w.balance FROM Wallet w WHERE w.id = :id")
    Optional<Double> findBalanceById(@Param("id") Long id);
}