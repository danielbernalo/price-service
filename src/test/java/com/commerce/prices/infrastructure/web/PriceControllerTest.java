package com.commerce.prices.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final String BASE_URL = "/api/prices";
    public static final Long PRODUCT_ID = 35455L;
    public static final Long NOT_FOUND_PRODUCT_ID = 200000L;
    public static final Long BRAND_ID = 1L;

    @Test
    void test1_Request_At_10AM_Day14() {
        // Test 1: petición a las 10:00 del día 14
        LocalDateTime requestDate = LocalDateTime.parse("2020-06-14T10:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.brandId").isEqualTo(BRAND_ID)
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.startDate").exists()
                .jsonPath("$.endDate").exists()
                .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void test2_Request_At_16PM_Day14() {
        // Test 2: petición a las 16:00 del día 14
        LocalDateTime requestDate = LocalDateTime.parse("2020-06-14T16:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.brandId").isEqualTo(BRAND_ID)
                .jsonPath("$.priceList").isEqualTo(2)
                .jsonPath("$.startDate").exists()
                .jsonPath("$.endDate").exists()
                .jsonPath("$.price").isEqualTo(25.45);
    }

    @Test
    void test3_Request_At_21PM_Day14() {
        // Test 3: petición a las 21:00 del día 14
        LocalDateTime requestDate = LocalDateTime.parse("2020-06-14T21:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.brandId").isEqualTo(BRAND_ID)
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.startDate").exists()
                .jsonPath("$.endDate").exists()
                .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void test4_Request_At_10AM_Day15() {
        // Test 4: petición a las 10:00 del día 15
        LocalDateTime requestDate = LocalDateTime.parse("2020-06-15T10:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.brandId").isEqualTo(BRAND_ID)
                .jsonPath("$.priceList").isEqualTo(3)
                .jsonPath("$.startDate").exists()
                .jsonPath("$.endDate").exists()
                .jsonPath("$.price").isEqualTo(30.50);
    }

    @Test
    void test5_Request_At_21PM_Day16() {
        // Test 5: petición a las 21:00 del día 16
        LocalDateTime requestDate = LocalDateTime.parse("2020-06-16T21:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.brandId").isEqualTo(BRAND_ID)
                .jsonPath("$.priceList").isEqualTo(4)
                .jsonPath("$.startDate").exists()
                .jsonPath("$.endDate").exists()
                .jsonPath("$.price").isEqualTo(38.95);
    }

    @Test
    void testNotFound_WhenNoPriceExists() {
        // Test adicional para verificar el caso cuando no existe precio
        LocalDateTime requestDate = LocalDateTime.parse("2019-06-14T10:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testBadRequest_WhenInvalidDateFormat() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", "invalid-date")
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").value(message ->
                        ((String) message).contains("Invalid parameter format"));
    }

    @Test
    void testBadRequest_WhenMissingRequiredParameterApplicationDate() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").value(message ->
                        ((String) message).contains("Required parameter"));
    }

    @Test
    void testBadRequest_WhenMissingRequiredParameterProductId() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").value(message ->
                        ((String) message).contains("Required parameter"));
    }

    @Test
    void testBadRequest_WhenMissingRequiredParameterBrandId() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .queryParam("productId", PRODUCT_ID)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").value(message ->
                        ((String) message).contains("Required parameter"));
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L})
    void testBadRequest_WhenInvalidProductId(Long invalidProductId) {
        LocalDateTime requestDate = LocalDateTime.parse("2020-06-14T10:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", invalidProductId)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").value(message ->
                        ((String) message).contains("Invalid product ID"));
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L})
    void testBadRequest_WhenInvalidBrandId(Long invalidBrandId) {
        LocalDateTime requestDate = LocalDateTime.parse("2020-06-14T10:00:00");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", requestDate.format(formatter))
                        .queryParam("productId", PRODUCT_ID)
                        .queryParam("brandId", invalidBrandId)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").value(message ->
                        ((String) message).contains("Invalid brand ID"));
    }

    @Test
    void testNotFoundPrice_WhenMissingProductIdNotExits() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", "2020-06-14T00:00:00")
                        .queryParam("productId", NOT_FOUND_PRODUCT_ID)
                        .queryParam("brandId", BRAND_ID)
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.message").value(message ->
                        ((String) message).contains("Price not found"));
    }

    @Test
    void test_concurrent_requests() {
        Flux.range(0, 100)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(i -> webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/api/prices")
                                .queryParam("applicationDate", "2020-06-14T10:00:00")
                                .queryParam("productId", 35455)
                                .queryParam("brandId", 1)
                                .build())
                        .exchange()
                        .returnResult(String.class)
                        .getResponseBody())
                .sequential()
                .blockLast();
    }

}