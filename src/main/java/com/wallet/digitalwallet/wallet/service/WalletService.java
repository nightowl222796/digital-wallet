package com.wallet.digitalwallet.wallet.service;

import com.wallet.digitalwallet.wallet.dto.AddMoneyRequestDto;
import com.wallet.digitalwallet.wallet.dto.WalletResponseDto;

public interface WalletService {

    // Create wallet for a user
    WalletResponseDto createWallet(Long userId);

    // Add money to wallet
    WalletResponseDto addMoney(AddMoneyRequestDto requestDto);

    // Get wallet details by user ID
    WalletResponseDto getWalletByUserId(Long userId);
}