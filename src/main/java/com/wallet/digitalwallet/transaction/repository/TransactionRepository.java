package com.wallet.digitalwallet.transaction.repository;

import com.wallet.digitalwallet.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    // ✅ Check if transaction already processed (prevent duplicates)
    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);

    // ✅ Get all transactions for a user (sent or received)
    @Query("SELECT t FROM Transaction t WHERE " +
           "t.sender.id = :userId OR t.receiver.id = :userId " +
           "ORDER BY t.createdAt DESC")
    List<Transaction> findAllTransactionsByUserId(
            @Param("userId") Long userId);

    List<Transaction> findBySenderIdOrderByCreatedAtDesc(Long senderId);
    List<Transaction> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);
}