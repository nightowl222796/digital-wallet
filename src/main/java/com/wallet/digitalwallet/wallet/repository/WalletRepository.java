package com.wallet.digitalwallet.wallet.repository;

import com.wallet.digitalwallet.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // ✅ Find wallet by user ID
    // SQL: SELECT * FROM wallets WHERE user_id = ?
    Optional<Wallet> findByUserId(Long userId);

    // ✅ Check if wallet already exists for user
    boolean existsByUserId(Long userId);
}