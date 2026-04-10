package com.wallet.digitalwallet.transaction.service;

import com.wallet.digitalwallet.common.exception.InsufficientBalanceException;
import com.wallet.digitalwallet.common.exception.ResourceNotFoundException;
import com.wallet.digitalwallet.common.exception.WalletException;
import com.wallet.digitalwallet.transaction.dto.SendMoneyRequestDto;
import com.wallet.digitalwallet.transaction.dto.TransactionResponseDto;
import com.wallet.digitalwallet.transaction.entity.Transaction;
import com.wallet.digitalwallet.transaction.entity.Transaction.TransactionStatus;
import com.wallet.digitalwallet.transaction.entity.Transaction.TransactionType;
import com.wallet.digitalwallet.transaction.repository.TransactionRepository;
import com.wallet.digitalwallet.user.entity.User;
import com.wallet.digitalwallet.user.repository.UserRepository;
import com.wallet.digitalwallet.wallet.entity.Wallet;
import com.wallet.digitalwallet.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                   WalletRepository walletRepository,
                                   UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public TransactionResponseDto sendMoney(SendMoneyRequestDto requestDto) {
        log.info("Send money: {} → {} | Amount: {}",
                requestDto.getSenderUserId(), requestDto.getReceiverUserId(), requestDto.getAmount());

        // Idempotency check
        String idempotencyKey = resolveIdempotencyKey(requestDto);
        Optional<Transaction> existing = transactionRepository
                .findByIdempotencyKey(idempotencyKey + "_DEBIT");
        if (existing.isPresent()) {
            log.warn("Duplicate transaction detected! Key: {}", idempotencyKey);
            Transaction t = existing.get();
            return mapToResponseDto(t, t.getSender(), t.getReceiver());
        }

        // Validation
        if (requestDto.getSenderUserId().equals(requestDto.getReceiverUserId())) {
            throw new WalletException("Sender and receiver cannot be the same person!");
        }

        // Load users
        User sender = userRepository.findById(requestDto.getSenderUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", requestDto.getSenderUserId()));

        User receiver = userRepository.findById(requestDto.getReceiverUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", requestDto.getReceiverUserId()));

        // Load wallets
        Wallet senderWallet = walletRepository.findByUserId(requestDto.getSenderUserId())
                .orElseThrow(() -> new WalletException("Sender wallet not found!"));

        Wallet receiverWallet = walletRepository.findByUserId(requestDto.getReceiverUserId())
                .orElseThrow(() -> new WalletException("Receiver wallet not found!"));

        // Balance check
        if (senderWallet.getBalance().compareTo(requestDto.getAmount()) < 0) {
            throw new InsufficientBalanceException(
                    senderWallet.getBalance().toString(),
                    requestDto.getAmount().toString());
        }

        // Deduct from sender
        BigDecimal senderNewBalance = senderWallet.getBalance().subtract(requestDto.getAmount());
        senderWallet.setBalance(senderNewBalance);
        walletRepository.save(senderWallet);

        // Add to receiver
        BigDecimal receiverNewBalance = receiverWallet.getBalance().add(requestDto.getAmount());
        receiverWallet.setBalance(receiverNewBalance);
        walletRepository.save(receiverWallet);

        // Save DEBIT transaction
        Transaction debitTxn = Transaction.builder()
                .sender(sender).receiver(receiver)
                .amount(requestDto.getAmount())
                .type(TransactionType.DEBIT)
                .status(TransactionStatus.SUCCESS)
                .description(requestDto.getDescription())
                .balanceAfter(senderNewBalance)
                .idempotencyKey(idempotencyKey + "_DEBIT")
                .build();
        transactionRepository.save(debitTxn);

        // Save CREDIT transaction
        Transaction creditTxn = Transaction.builder()
                .sender(sender).receiver(receiver)
                .amount(requestDto.getAmount())
                .type(TransactionType.CREDIT)
                .status(TransactionStatus.SUCCESS)
                .description(requestDto.getDescription())
                .balanceAfter(receiverNewBalance)
                .idempotencyKey(idempotencyKey + "_CREDIT")
                .build();
        transactionRepository.save(creditTxn);

        log.info("✅ Transfer complete: {} → {} | ₹{}",
                sender.getName(), receiver.getName(), requestDto.getAmount());

        return mapToResponseDto(debitTxn, sender, receiver);
    }

    @Override
    public List<TransactionResponseDto> getTransactionHistory(Long userId) {
        log.info("Fetching history for user ID: {}", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return transactionRepository.findAllTransactionsByUserId(userId)
                .stream()
                .map(t -> mapToResponseDto(t, t.getSender(), t.getReceiver()))
                .collect(Collectors.toList());
    }

    private String resolveIdempotencyKey(SendMoneyRequestDto requestDto) {
        if (requestDto.getIdempotencyKey() != null && !requestDto.getIdempotencyKey().isEmpty()) {
            return requestDto.getIdempotencyKey();
        }
        return requestDto.getSenderUserId() + "_"
                + requestDto.getReceiverUserId() + "_"
                + requestDto.getAmount() + "_"
                + Instant.now().getEpochSecond();
    }

    private TransactionResponseDto mapToResponseDto(Transaction t, User sender, User receiver) {
        return TransactionResponseDto.builder()
                .transactionId(t.getId())
                .senderUserId(sender.getId())
                .senderName(sender.getName())
                .receiverUserId(receiver.getId())
                .receiverName(receiver.getName())
                .amount(t.getAmount())
                .type(t.getType())
                .status(t.getStatus())
                .description(t.getDescription())
                .balanceAfter(t.getBalanceAfter())
                .createdAt(t.getCreatedAt())
                .build();
    }
}