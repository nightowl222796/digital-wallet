package com.wallet.digitalwallet.user.service;

import com.wallet.digitalwallet.user.dto.UserRequestDto;
import com.wallet.digitalwallet.user.dto.UserResponseDto;

// ✅ Interface = contract
// Defines WHAT the service does, not HOW
// This is a best practice — coding to interface
public interface UserService {

    // Register a new user
    UserResponseDto registerUser(UserRequestDto requestDto);

    // Get user by ID
    UserResponseDto getUserById(Long id);

    // Get user by phone number
    UserResponseDto getUserByPhone(String phoneNumber);
}