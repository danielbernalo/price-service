package com.commerce.prices.infrastructure.repository;

import com.commerce.prices.adapter.persistence.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceRepository extends ReactiveCrudRepository<PriceEntity, Long> {
    @Query("SELECT * FROM prices " +
            "WHERE product_id = :productId " +
            "AND brand_id = :brandId " +
            "AND :applicationDate BETWEEN start_date AND end_date " +
            "ORDER BY priority DESC LIMIT 1")
    Mono<PriceEntity> findPriceByDateProductAndBrand(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    );
}
