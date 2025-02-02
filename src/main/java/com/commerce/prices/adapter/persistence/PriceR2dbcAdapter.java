package com.commerce.prices.adapter.persistence;

import com.commerce.prices.application.port.out.LoadPricePort;
import com.commerce.prices.domain.Price;
import com.commerce.prices.infrastructure.repository.PriceRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class PriceR2dbcAdapter implements LoadPricePort {
    private final PriceRepository priceRepository;


    public PriceR2dbcAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Mono<Price> findPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findPriceByDateProductAndBrand(applicationDate, productId, brandId)
                .map(PriceMapper::toDomain);
    }
}
