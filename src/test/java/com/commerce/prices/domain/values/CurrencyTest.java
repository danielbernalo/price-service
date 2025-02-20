package com.commerce.prices.domain.values;


import com.commerce.prices.domain.exception.InvalidCurrencyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @Test
    void should_create_valid_currency() {
        // when
        Currency currency = Currency.of("EUR");

        // then
        assertNotNull(currency);
        assertEquals("EUR", currency.getCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "EURO", "123", "EU"})
    void should_throw_exception_for_invalid_currency_code(String invalidCode) {
        // when/then
        assertThrows(InvalidCurrencyException.class, () -> Currency.of(invalidCode));
    }

    @Test
    void should_equal_same_currency() {
        // given
        Currency currency1 = Currency.of("EUR");
        Currency currency2 = Currency.of("EUR");

        // when/then
        assertEquals(currency1, currency2);
        assertEquals(currency1.hashCode(), currency2.hashCode());
    }
}