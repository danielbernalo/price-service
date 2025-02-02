package com.commerce.prices.application.port.in;

import com.commerce.prices.application.dto.PriceResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface GetPriceUseCase {
    Mono<PriceResponse> getPriceByDateProductAndBrand(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    );
}
