package com.wallet.digitalwallet.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMoneyRequestDto {

    @NotNull(message = "Sender ID is required")
    private Long senderUserId;

    @NotNull(message = "Receiver ID is required")
    private Long receiverUserId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00", message = "Minimum transfer amount is 1.00")
    private BigDecimal amount;

    private String description;

    // ✅ Client sends this unique key to prevent double payments
    // Example: "pay-user1-user2-1712345678"
    // If same key is sent twice, second request is ignored
    private String idempotencyKey;
}