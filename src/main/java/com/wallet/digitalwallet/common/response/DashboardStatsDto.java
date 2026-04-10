package com.wallet.digitalwallet.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {

    private long totalUsers;
    private long totalWallets;
    private long totalTransactions;
    private long totalCredits;
    private long totalDebits;
    private BigDecimal totalMoneyInSystem;
    private BigDecimal totalMoneyTransferred;
}