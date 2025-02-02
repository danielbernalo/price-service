package com.commerce.prices.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

@SpringBootTest
class DatabaseMigrationTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    void testMigrationSuccess() {
        databaseClient
                .sql("SELECT COUNT(*) FROM prices")
                .map(row -> row.get(0, Long.class))
                .one()
                .as(StepVerifier::create)
                .expectNext(4L)
                .verifyComplete();
    }
}