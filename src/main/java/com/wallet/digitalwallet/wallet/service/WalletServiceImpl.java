package com.wallet.digitalwallet.wallet.service;

import com.wallet.digitalwallet.common.exception.ResourceNotFoundException;
import com.wallet.digitalwallet.common.exception.WalletException;
import com.wallet.digitalwallet.user.entity.User;
import com.wallet.digitalwallet.user.repository.UserRepository;
import com.wallet.digitalwallet.wallet.dto.AddMoneyRequestDto;
import com.wallet.digitalwallet.wallet.dto.WalletResponseDto;
import com.wallet.digitalwallet.wallet.entity.Wallet;
import com.wallet.digitalwallet.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public WalletResponseDto createWallet(Long userId) {
        log.info("Creating wallet for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (walletRepository.existsByUserId(userId)) {
            throw new WalletException("Wallet already exists for user ID: " + userId);
        }

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();

        Wallet savedWallet = walletRepository.save(wallet);
        log.info("Wallet created with ID: {}", savedWallet.getId());
        return mapToResponseDto(savedWallet);
    }

    @Override
    @Transactional
    public WalletResponseDto addMoney(AddMoneyRequestDto requestDto) {
        log.info("Adding {} to wallet of user ID: {}", requestDto.getAmount(), requestDto.getUserId());

        Wallet wallet = walletRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wallet", "userId", String.valueOf(requestDto.getUserId())));

        BigDecimal newBalance = wallet.getBalance().add(requestDto.getAmount());
        wallet.setBalance(newBalance);
        Wallet updatedWallet = walletRepository.save(wallet);

        log.info("Money added. New balance for user {}: {}", requestDto.getUserId(), newBalance);
        return mapToResponseDto(updatedWallet);
    }

    @Override
    public WalletResponseDto getWalletByUserId(Long userId) {
        log.info("Fetching wallet for user ID: {}", userId);
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wallet", "userId", String.valueOf(userId)));
        return mapToResponseDto(wallet);
    }

    private WalletResponseDto mapToResponseDto(Wallet wallet) {
        return WalletResponseDto.builder()
                .walletId(wallet.getId())
                .userId(wallet.getUser().getId())
                .userName(wallet.getUser().getName())
                .userPhone(wallet.getUser().getPhoneNumber())
                .balance(wallet.getBalance())
                .createdAt(wallet.getCreatedAt())
                .build();
    }
}