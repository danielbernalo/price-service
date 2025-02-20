package com.commerce.prices.domain.repository;

import com.commerce.prices.domain.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceRepository{
    /**
     * Finds the applicable price for a product from a brand at a specific date
     * considering the priority rules.
     */
    Mono<Price> findByProductAndBrandAndDate(Long productId, Long brandId, LocalDateTime date);
}
