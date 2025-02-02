package com.commerce.prices.domain.exception;

public class PriceNotFoundException  extends RuntimeException {
    public PriceNotFoundException(final String priceNotFound) {
        super(priceNotFound);
    }
}
