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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void createWallet_ShouldReturnSavedWalletWithGeneratedId() {

        UUID expectedId = UUID.randomUUID();
        Wallet inputWallet = new Wallet();
        inputWallet.setOperationType(OperationType.DEPOSIT);
        inputWallet.setBalance(1000.0);

        Wallet savedWallet = new Wallet();
        savedWallet.setWalletId(expectedId);
        savedWallet.setOperationType(inputWallet.getOperationType());
        savedWallet.setBalance(inputWallet.getBalance());

        when(walletRepository.save(inputWallet)).thenReturn(savedWallet);

        Wallet result = walletService.createWallet(inputWallet);

        assertNotNull(result);
        assertEquals(expectedId, result.getWalletId());
        assertEquals(OperationType.DEPOSIT, result.getOperationType());
        assertEquals(1000.0, result.getBalance());
        verify(walletRepository, timeout(1)).save(inputWallet);
    }

    @Test
    void createWallet_ShouldPreserveAllFields() {

        UUID expectedId = UUID.randomUUID();
        Wallet inputWallet = new Wallet();
        inputWallet.setOperationType(OperationType.WITHDRAW);
        inputWallet.setBalance(500);

        when(walletRepository.save(any(Wallet.class)))
                .thenAnswer(invocationOnMock -> {
                    Wallet w = invocationOnMock.getArgument(0);
                    w.setWalletId(expectedId);
                    return w;
                });

        Wallet result = walletService.createWallet(inputWallet);

        assertNotNull(result.getWalletId());
        assertEquals(expectedId, result.getWalletId());
        assertEquals(OperationType.WITHDRAW, result.getOperationType());
        assertEquals(500.0, result.getBalance(), 0.001);
    }

    @Test
    void createWallet_ShouldCallRepositoryOnce() {

        Wallet wallet = new Wallet();
        wallet.setBalance(100.0);
        wallet.setOperationType(OperationType.DEPOSIT);

        when(walletRepository.save(wallet)).thenReturn(wallet);

        walletService.createWallet(wallet);

        verify(walletRepository, times(1)).save(wallet);
    }
}