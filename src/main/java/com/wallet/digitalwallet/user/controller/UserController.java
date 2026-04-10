package com.wallet.digitalwallet.user.controller;

import com.wallet.digitalwallet.common.response.ApiResponse;
import com.wallet.digitalwallet.user.dto.UserRequestDto;
import com.wallet.digitalwallet.user.dto.UserResponseDto;
import com.wallet.digitalwallet.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User registration and management")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account in the system")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(
            @Valid @RequestBody UserRequestDto requestDto) {
        log.info("POST /api/users/register called");
        UserResponseDto response = userService.registerUser(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", response));
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(
            @PathVariable Long id) {
        log.info("GET /api/users/{} called", id);
        UserResponseDto response = userService.getUserById(id);
        return ResponseEntity.ok(
                ApiResponse.success("User fetched successfully", response));
    }

    @Operation(summary = "Get user by phone number")
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserByPhone(
            @PathVariable String phoneNumber) {
        log.info("GET /api/users/phone/{} called", phoneNumber);
        UserResponseDto response = userService.getUserByPhone(phoneNumber);
        return ResponseEntity.ok(
                ApiResponse.success("User fetched successfully", response));
    }
}