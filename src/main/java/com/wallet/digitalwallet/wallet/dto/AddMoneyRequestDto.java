package com.wallet.digitalwallet.wallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMoneyRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    // ✅ Minimum amount is 1.00 — can't add 0 or negative money
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00", message = "Minimum amount to add is 1.00")
    private BigDecimal amount;
}