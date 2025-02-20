package com.commerce.prices.domain.exception;

public class InvalidMoneyAmountException extends DomainException {
    public InvalidMoneyAmountException(String message) {
        super(message);
    }
}
