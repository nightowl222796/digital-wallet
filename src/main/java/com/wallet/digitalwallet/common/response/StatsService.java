package com.wallet.digitalwallet.common.response;

import com.wallet.digitalwallet.transaction.entity.Transaction;
import com.wallet.digitalwallet.transaction.repository.TransactionRepository;
import com.wallet.digitalwallet.user.repository.UserRepository;
import com.wallet.digitalwallet.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public DashboardStatsDto getDashboardStats() {
        List<Transaction> all = transactionRepository.findAll();

        long credits = all.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.CREDIT).count();
        long debits = all.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.DEBIT).count();

        BigDecimal totalInSystem = walletRepository.findAll().stream()
                .map(w -> w.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTransferred = all.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.DEBIT)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardStatsDto.builder()
                .totalUsers(userRepository.count())
                .totalWallets(walletRepository.count())
                .totalTransactions(all.size())
                .totalCredits(credits)
                .totalDebits(debits)
                .totalMoneyInSystem(totalInSystem)
                .totalMoneyTransferred(totalTransferred)
                .build();
    }
}