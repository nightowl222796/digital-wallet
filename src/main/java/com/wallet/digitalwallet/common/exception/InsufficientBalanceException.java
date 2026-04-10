package com.wallet.digitalwallet.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String available, String required) {
        super("Insufficient balance! Available: ₹" + available + " | Required: ₹" + required);
    }
}