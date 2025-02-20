package com.commerce.prices.domain.exception;

public class CurrencyMismatchException extends DomainException {
    public CurrencyMismatchException(String message) {
        super(message);
    }
}
