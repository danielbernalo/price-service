package com.commerce.prices.domain;

import com.commerce.prices.domain.values.DateRange;
import com.commerce.prices.domain.values.Money;
import com.commerce.prices.domain.exception.InvalidPriorityException;
import com.commerce.prices.domain.values.PriceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    @DisplayName("Should create a valid price")
    void shouldCreateValidPrice() {
        // Given
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);

        // When
        Price price = new Price.Builder()
                .id(PriceId.of(1L, 35455L, "EUR"))
                .dateRange(DateRange.of(startDate, endDate))
                .priceList(1L)
                .priority(0)
                .price(Money.of(new BigDecimal("35.50"), "EUR"))
                .build();

        // Then
        assertNotNull(price);
        assertTrue(price.isApplicableAt(startDate.plusHours(1)));

    }

    @Test
    @DisplayName("Should throw exception when priority is negative")
    void shouldThrowExceptionWhenPriorityIsNegative() {
        // Given
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-31T23:59:59");

        // When/Then
        assertThrows(InvalidPriorityException.class, () -> {
            new Price.Builder()
                    .id(PriceId.of(1L, 35455L, "EUR"))
                    .dateRange(DateRange.of(startDate, endDate))
                    .priceList(1L)
                    .priority(-1)
                    .price(Money.of(new BigDecimal("35.50"), "EUR"))
                    .build();
        });
    }

    @Test
    @DisplayName("Should correctly determine if price is applicable at given date")
    void shouldDetermineIfPriceIsApplicable() {
        // Given
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-31T23:59:59");
        Price price = new Price.Builder()
                .id(PriceId.of(1L, 35455L, "EUR"))
                .dateRange(DateRange.of(startDate, endDate))
                .priceList(1L)
                .priority(0)
                .price(Money.of(new BigDecimal("35.50"), "EUR"))
                .build();

        // When/Then
        assertTrue(price.isApplicableAt(LocalDateTime.parse("2020-07-01T10:00:00")));
        assertFalse(price.isApplicableAt(LocalDateTime.parse("2021-01-01T00:00:00")));
    }

    @Test
    @DisplayName("Should correctly compare priorities between prices")
    void shouldComparePriorities() {
        // Given
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-31T23:59:59");

        Price price1 = new Price.Builder()
                .id(PriceId.of(1L, 35455L, "EUR"))
                .dateRange(DateRange.of(startDate, endDate))
                .priceList(1L)
                .priority(0)
                .price(Money.of(new BigDecimal("35.50"), "EUR"))
                .build();

        Price price2 = new Price.Builder()
                .id(PriceId.of(1L, 35455L, "EUR"))
                .dateRange(DateRange.of(startDate, endDate))
                .priceList(2L)
                .priority(1)
                .price(Money.of(new BigDecimal("25.45"), "EUR"))
                .build();

        // When/Then
        assertFalse(price1.hasPriorityOver(price2));
        assertTrue(price2.hasPriorityOver(price1));
    }
}