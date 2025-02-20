package com.commerce.prices.config;

import com.commerce.prices.domain.repository.PriceRepository;
import com.commerce.prices.infrastructure.persistence.PricePersistenceMapper;
import com.commerce.prices.infrastructure.persistence.PriceRepositoryImpl;
import com.commerce.prices.infrastructure.persistence.PriceR2dbcRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceConfig {
    @Bean
    public PriceRepository priceRepository(PriceR2dbcRepository priceRepository, PricePersistenceMapper pricePersistenceMapper) {
        return new PriceRepositoryImpl(priceRepository, pricePersistenceMapper);
    }
}
