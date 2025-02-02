package com.commerce.prices.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlywayInitializer {

    private final Flyway flyway;

    @PostConstruct
    public void migrateFlyway() {
        flyway.migrate();
    }
}