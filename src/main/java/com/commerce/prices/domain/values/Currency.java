package com.commerce.prices.domain.values;

import com.commerce.prices.domain.common.ValueObject;
import com.commerce.prices.domain.exception.InvalidCurrencyException;

import java.util.Objects;

public class Currency implements ValueObject {
    private final java.util.Currency currency;

    private Currency(java.util.Currency currency) {
        this.currency = Objects.requireNonNull(currency, "Currency must not be null");
    }
    public static Currency of(String isoCode) {
        try {
            return new Currency(java.util.Currency.getInstance(isoCode));
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException("Invalid currency code: " + isoCode);
        }
    }
    public String getCode(){
        return currency.getCurrencyCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency that = (Currency) o;
        return currency.equals(that.currency);
    }
    @Override
    public int hashCode() {
        return Objects.hash(currency);
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode();
    }
}
