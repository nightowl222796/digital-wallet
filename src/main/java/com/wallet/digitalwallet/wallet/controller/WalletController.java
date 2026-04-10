package com.wallet.digitalwallet.wallet.controller;

import com.wallet.digitalwallet.common.response.ApiResponse;
import com.wallet.digitalwallet.wallet.dto.AddMoneyRequestDto;
import com.wallet.digitalwallet.wallet.dto.WalletResponseDto;
import com.wallet.digitalwallet.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Tag(name = "Wallets", description = "Wallet creation and balance management")
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "Create wallet for user", description = "Creates a new wallet with 0 balance for the given user ID")
    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse<WalletResponseDto>> createWallet(
            @PathVariable Long userId) {
        log.info("POST /api/wallets/create/{} called", userId);
        WalletResponseDto response = walletService.createWallet(userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Wallet created successfully", response));
    }

    @Operation(summary = "Add money to wallet", description = "Adds the specified amount to the user's wallet balance")
    @PostMapping("/add-money")
    public ResponseEntity<ApiResponse<WalletResponseDto>> addMoney(
            @Valid @RequestBody AddMoneyRequestDto requestDto) {
        log.info("POST /api/wallets/add-money called for user: {}", requestDto.getUserId());
        WalletResponseDto response = walletService.addMoney(requestDto);
        return ResponseEntity.ok(
                ApiResponse.success("Money added successfully", response));
    }

    @Operation(summary = "Get wallet balance", description = "Returns wallet details and current balance for the given user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<WalletResponseDto>> getWallet(
            @PathVariable Long userId) {
        log.info("GET /api/wallets/user/{} called", userId);
        WalletResponseDto response = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Wallet fetched successfully", response));
    }
}