package com.wallet.digitalwallet.auth.controller;

import com.wallet.digitalwallet.auth.dto.LoginRequestDto;
import com.wallet.digitalwallet.auth.dto.LoginResponseDto;
import com.wallet.digitalwallet.auth.service.AuthService;
import com.wallet.digitalwallet.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Login and authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login with phone and password")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", authService.login(requestDto)));
    }
}