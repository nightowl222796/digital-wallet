package com.wallet.digitalwallet.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank private String phoneNumber;
    @NotBlank private String password;
}