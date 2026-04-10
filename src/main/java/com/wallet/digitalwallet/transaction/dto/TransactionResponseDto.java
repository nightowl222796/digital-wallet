package com.wallet.digitalwallet.transaction.dto;

import com.wallet.digitalwallet.transaction.entity.Transaction.TransactionStatus;
import com.wallet.digitalwallet.transaction.entity.Transaction.TransactionType;
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
public class TransactionResponseDto {

    private Long transactionId;
    private Long senderUserId;
    private String senderName;
    private Long receiverUserId;
    private String receiverName;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String description;
    private BigDecimal balanceAfter;
    private LocalDateTime createdAt;
}