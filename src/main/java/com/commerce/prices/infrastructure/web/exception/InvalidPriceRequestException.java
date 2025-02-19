package com.commerce.prices.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPriceRequestException extends RuntimeException {
    public InvalidPriceRequestException(String message) {
        super(message);
    }
}
