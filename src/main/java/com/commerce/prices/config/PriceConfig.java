package com.commerce.prices.config;

import com.commerce.prices.adapter.persistence.PriceR2dbcAdapter;
import com.commerce.prices.application.port.out.LoadPricePort;
import com.commerce.prices.infrastructure.repository.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceConfig {
    @Bean
    public LoadPricePort loadPricePort(PriceRepository priceRepository) {
        return new PriceR2dbcAdapter(priceRepository);
    }
}
