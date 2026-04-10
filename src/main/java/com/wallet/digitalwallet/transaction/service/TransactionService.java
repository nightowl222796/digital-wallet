package com.wallet.digitalwallet.transaction.service;

import com.wallet.digitalwallet.transaction.dto.SendMoneyRequestDto;
import com.wallet.digitalwallet.transaction.dto.TransactionResponseDto;
import java.util.List;

public interface TransactionService {

    // Send money from one user to another
    TransactionResponseDto sendMoney(SendMoneyRequestDto requestDto);

    // Get all transactions of a user
    List<TransactionResponseDto> getTransactionHistory(Long userId);
}