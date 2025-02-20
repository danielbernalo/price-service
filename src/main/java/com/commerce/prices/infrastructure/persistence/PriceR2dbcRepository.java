package com.commerce.prices.infrastructure.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceR2dbcRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    @Query("""
            SELECT * FROM prices 
            WHERE product_id = :productId 
            AND brand_id = :brandId 
            AND :date BETWEEN start_date AND end_date 
            ORDER BY priority DESC 
            LIMIT 1
            """)
    Mono<PriceEntity> findFirstByProductAndBrandAndDate(
            Long productId,
            Long brandId,
            LocalDateTime date
    );
}