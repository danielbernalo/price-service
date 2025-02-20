package com.commerce.prices.infrastructure.web;

import com.commerce.prices.application.port.in.GetPriceUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(PriceController.class)
class PriceControllerHandlerErrorsTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GetPriceUseCase getPriceUseCase;

    @Test
    void should_return_500_when_unexpected_error_occurs() {
        // given
        when(getPriceUseCase.getPriceByDateProductAndBrand(any(), any(), any()))
                .thenReturn(Mono.error(new RuntimeException("Unexpected database error")));

        // when/then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/prices")
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.status").isEqualTo(500)
                .jsonPath("$.message").isEqualTo("An unexpected error occurred");
    }


    @Test
    void should_return_500_when_unexpected_service_error() {
        // given
        String errorMessage = "Service unavailable";
        when(getPriceUseCase.getPriceByDateProductAndBrand(any(), any(), any()))
                .thenReturn(Mono.error(new IllegalStateException(errorMessage)));

        // when/then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/prices")
                        .queryParam("applicationDate", LocalDateTime.now().toString())
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.status").isEqualTo(500)
                .jsonPath("$.message").isEqualTo("An unexpected error occurred");
    }
}