package com.commerce.prices.domain.values;

import com.commerce.prices.domain.common.ValueObject;
import com.commerce.prices.domain.exception.CurrencyMismatchException;
import com.commerce.prices.domain.exception.InvalidMoneyAmountException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Money implements ValueObject {
    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMoneyAmountException("Amount cannot be negative");
        }
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.of(currencyCode));
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new CurrencyMismatchException("Cannot add different currencies");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 &&
                currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}