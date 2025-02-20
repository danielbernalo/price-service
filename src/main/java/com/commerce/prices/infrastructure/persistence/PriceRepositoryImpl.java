package com.commerce.prices.infrastructure.persistence;

import com.commerce.prices.domain.Price;
import com.commerce.prices.domain.repository.PriceRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceR2dbcRepository r2dbcRepository;
    private final PricePersistenceMapper mapper;

    public PriceRepositoryImpl(final PriceR2dbcRepository r2dbcRepository, final PricePersistenceMapper mapper) {
        this.r2dbcRepository = r2dbcRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Price> findByProductAndBrandAndDate(Long productId, Long brandId, LocalDateTime date) {
        return r2dbcRepository.findFirstByProductAndBrandAndDate(productId, brandId, date)
                .map(mapper::toDomain);
    }
}