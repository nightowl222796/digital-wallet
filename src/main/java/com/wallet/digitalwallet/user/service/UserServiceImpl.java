package com.wallet.digitalwallet.user.service;

import com.wallet.digitalwallet.common.exception.DuplicateResourceException;
import com.wallet.digitalwallet.common.exception.ResourceNotFoundException;
import com.wallet.digitalwallet.user.dto.UserRequestDto;
import com.wallet.digitalwallet.user.dto.UserResponseDto;
import com.wallet.digitalwallet.user.entity.User;
import com.wallet.digitalwallet.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto requestDto) {
        log.info("Registering: {}", requestDto.getPhoneNumber());
        log.info("Password received: {}", requestDto.getPassword());

        if (userRepository.existsByPhoneNumber(requestDto.getPhoneNumber()))
            throw new DuplicateResourceException("User", "phone", requestDto.getPhoneNumber());

        if (userRepository.existsByEmail(requestDto.getEmail()))
            throw new DuplicateResourceException("User", "email", requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        log.info("Encoded password: {}", encodedPassword);

        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .password(encodedPassword)
                .build();

        User saved = userRepository.save(user);
        log.info("User saved with ID: {}", saved.getId());
        return mapToDto(saved);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return mapToDto(user);
    }

    @Override
    public UserResponseDto getUserByPhone(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User", "phone", phoneNumber));
        return mapToDto(user);
    }

    private UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }
}