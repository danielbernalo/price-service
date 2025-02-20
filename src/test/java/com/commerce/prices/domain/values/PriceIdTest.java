package com.commerce.prices.domain.values;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PriceIdTest {

    @Test
    void should_create_valid_price_id() {
        PriceId priceId = PriceId.of(1L, 1L, "USD");

        assertNotNull(priceId);
        assertEquals(1L, priceId.getBrandId());
        assertEquals(1L, priceId.getProductId());
        assertEquals("USD", priceId.getCurrency().getCode());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1, USD, Brand ID must be positive",
            "1, 0, EUR, Product ID must be positive"
    })
    void should_throw_exception_for_invalid_price_id(Long brandId, Long productId, String currency, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> PriceId.of(brandId, productId, currency));
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    void should_equal_same_price_id() {
        PriceId priceId1 = PriceId.of(1L, 1L, "USD");
        PriceId priceId2 = PriceId.of(1L, 1L, "USD");

        assertEquals(priceId1, priceId2);
        assertEquals(priceId1.hashCode(), priceId2.hashCode());
    }

    @Test
    void should_not_equal_different_price_id() {
        PriceId priceId1 = PriceId.of(1L, 1L, "USD");
        PriceId priceId2 = PriceId.of(2L, 1L, "USD");

        assertNotEquals(priceId1, priceId2);
    }

    @Test
    void should_return_price_id_as_string() {
        PriceId priceId = PriceId.of(1L, 1L, "USD");

        assertEquals("PriceId{brandId=1, productId=1, currency='USD'}", priceId.toString());
    }
}