package com.wallet.digitalwallet.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDto {

    private Long walletId;
    private Long userId;
    private String userName;
    private String userPhone;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}