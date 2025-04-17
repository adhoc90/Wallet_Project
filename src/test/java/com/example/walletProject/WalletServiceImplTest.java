package com.example.walletProject;

import com.example.walletProject.enums.OperationType;
import com.example.walletProject.exception.InsufficientFundsException;
import com.example.walletProject.exception.WalletNotFoundException;
import com.example.walletProject.model.Wallet;
import com.example.walletProject.repository.WalletRepository;
import com.example.walletProject.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void testGetWalletBalance_whenWalletExists_shouldReturnBalance() {

        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setBalance(100.0);

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Double balance = walletService.getWalletBalance(walletId);

        assertEquals(100.0, balance);
        verify(walletRepository).findById(walletId);
    }

    @Test
    void testGetWalletBalance_whenWalletNotExists_shouldThrowException() {

        UUID walletId = UUID.randomUUID();

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        assertThrows(WalletNotFoundException.class, () -> walletService.getWalletBalance(walletId));
        verify(walletRepository).findById(walletId);
    }

    @Test
    void updateWalletBalance_depositOperation_shouldIncreaseBalance() {

        UUID walledId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setBalance(100.0);

        when(walletRepository.findById(walledId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        walletService.updateWalletBalance(walledId, OperationType.DEPOSIT, 50.0);

        assertEquals(150.0, wallet.getBalance());
        verify(walletRepository).findById(walledId);
        verify(walletRepository).save(wallet);
    }

    @Test
    void updateWalletBalance_withDrawOperation_shouldDecreaseBalance() {

        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setBalance(100.0);

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        walletService.updateWalletBalance(walletId, OperationType.WITHDRAW, 50.0);
        assertEquals(50.0, wallet.getBalance());
        verify(walletRepository).findById(walletId);
        verify(walletRepository).save(wallet);
    }


    @Test
    void updateWalletBalance_withdrawOperationWithInsufficientFunds_shouldThrowException() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setBalance(50.0);

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class, () ->
                walletService.updateWalletBalance(walletId, OperationType.WITHDRAW, 100.0));

        verify(walletRepository).findById(walletId);
        verify(walletRepository, never()).save(any());
    }

    @Test
    void updateWalletBalance_whenWalletNotExists_shouldThrowException() {

        UUID walletId = UUID.randomUUID();

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () ->
                walletService.updateWalletBalance(walletId, OperationType.DEPOSIT, 100.0));
        verify(walletRepository).findById(walletId);
        verify(walletRepository, never()).save(any());
    }
}