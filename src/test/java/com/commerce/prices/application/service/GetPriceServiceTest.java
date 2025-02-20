package com.commerce.prices.application.service;

import com.commerce.prices.application.dto.PriceResponse;
import com.commerce.prices.domain.Price;
import com.commerce.prices.domain.repository.PriceRepository;
import com.commerce.prices.domain.values.DateRange;
import com.commerce.prices.domain.values.Money;
import com.commerce.prices.domain.values.PriceId;
import com.commerce.prices.infrastructure.web.exception.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GetPriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    private GetPriceService getPriceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getPriceService = new GetPriceService(priceRepository);
    }

    @Test
    @DisplayName("Should return price when found")
    void shouldReturnPriceWhenFound() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Long productId = 35455L;
        Long brandId = 1L;
        String currencyId = "EUR";

        Price price = new Price.Builder()
                .id(PriceId.of(brandId, productId, currencyId))
                .dateRange(DateRange.of(
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59")))
                .priceList(1L)
                .priority(0)
                .price(Money.of(new BigDecimal("35.50"), "EUR"))
                .build();

        when(priceRepository.findByProductAndBrandAndDate(eq(productId), eq(brandId), any()))
                .thenReturn(Mono.just(price));

        // When
        Mono<PriceResponse> result = getPriceService
                .getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getProductId().equals(productId) &&
                                response.getBrandId().equals(brandId) &&
                                response.getPrice().compareTo(new BigDecimal("35.50")) == 0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty when no price found")
    void shouldReturnEmptyWhenNoPriceFound() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepository.findByProductAndBrandAndDate(eq(productId), eq(brandId), any()))
                .thenReturn(Mono.empty());

        // When
        Mono<PriceResponse> result = getPriceService
                .getPriceByDateProductAndBrand(applicationDate, productId, brandId);

        // Then
        StepVerifier.create(result)
                .expectError(PriceNotFoundException.class)
                .verify();
    }
}