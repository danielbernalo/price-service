package com.commerce.prices.domain.values;

import com.commerce.prices.domain.exception.CurrencyMismatchException;
import com.commerce.prices.domain.exception.InvalidMoneyAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void should_create_valid_money() {
        // when
        Money money = Money.of(BigDecimal.TEN, "EUR");

        // then
        assertNotNull(money);
        assertEquals(0, BigDecimal.TEN.compareTo(money.getAmount()));
        assertEquals("EUR", money.getCurrency().getCode());
    }

    @Test
    void should_throw_exception_for_negative_amount() {
        // when/then
        assertThrows(InvalidMoneyAmountException.class,
                () -> Money.of(BigDecimal.valueOf(-10), "EUR"));
    }

    @Test
    void should_add_same_currency() {
        // given
        Money money1 = Money.of(BigDecimal.TEN, "EUR");
        Money money2 = Money.of(BigDecimal.ONE, "EUR");

        // when
        Money result = money1.add(money2);

        // then
        assertEquals(0, new BigDecimal("11").compareTo(result.getAmount()));
        assertEquals("EUR", result.getCurrency().getCode());
    }

    @Test
    void should_throw_exception_when_adding_different_currencies() {
        // given
        Money money1 = Money.of(BigDecimal.TEN, "EUR");
        Money money2 = Money.of(BigDecimal.ONE, "USD");

        // when/then
        assertThrows(CurrencyMismatchException.class, () -> money1.add(money2));
    }

    @Test
    void should_equal_same_money() {
        // given
        Money money1 = Money.of(BigDecimal.TEN, "EUR");
        Money money2 = Money.of(BigDecimal.TEN, "EUR");

        // when/then
        assertEquals(money1, money2);
        assertEquals(money1.hashCode(), money2.hashCode());
    }
}