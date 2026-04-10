package com.wallet.digitalwallet.auth.service;

import com.wallet.digitalwallet.auth.dto.LoginRequestDto;
import com.wallet.digitalwallet.auth.dto.LoginResponseDto;
import com.wallet.digitalwallet.auth.security.JwtUtil;
import com.wallet.digitalwallet.common.exception.ResourceNotFoundException;
import com.wallet.digitalwallet.user.entity.User;
import com.wallet.digitalwallet.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByPhoneNumber(requestDto.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User", "phone", requestDto.getPhoneNumber()));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid password!");

        String token = jwtUtil.generateToken(user.getPhoneNumber(), user.getId());
        log.info("Login successful: {}", user.getPhoneNumber());

        return LoginResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}