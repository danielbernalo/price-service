package com.commerce.prices.application.port.out;

import com.commerce.prices.domain.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface LoadPricePort {
    Mono<Price> findPrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    );
}
