package com.wallet.digitalwallet.transaction.controller;

import com.wallet.digitalwallet.common.response.ApiResponse;
import com.wallet.digitalwallet.transaction.dto.SendMoneyRequestDto;
import com.wallet.digitalwallet.transaction.dto.TransactionResponseDto;
import com.wallet.digitalwallet.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Send money and view transaction history")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(
        summary = "Send money between users",
        description = "Transfers money with idempotency protection and REPEATABLE_READ isolation. " +
                      "Deducts from sender and credits receiver atomically."
    )
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<TransactionResponseDto>> sendMoney(
            @Valid @RequestBody SendMoneyRequestDto requestDto) {
        log.info("POST /api/transactions/send called");
        TransactionResponseDto response = transactionService.sendMoney(requestDto);
        return ResponseEntity.ok(
                ApiResponse.success("Money sent successfully!", response));
    }

    @Operation(
        summary = "Get transaction history",
        description = "Returns all transactions where the user is sender OR receiver, ordered by latest first"
    )
    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse<List<TransactionResponseDto>>> getTransactionHistory(
            @PathVariable Long userId) {
        log.info("GET /api/transactions/history/{} called", userId);
        List<TransactionResponseDto> response = transactionService.getTransactionHistory(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Transaction history fetched", response));
    }
}