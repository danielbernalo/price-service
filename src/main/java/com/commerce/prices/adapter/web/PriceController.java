package com.commerce.prices.adapter.web;

import com.commerce.prices.application.dto.PriceResponse;
import com.commerce.prices.application.port.in.GetPriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final GetPriceUseCase getPriceUseCase;

    public PriceController(final GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @Operation(
            summary = "Get price for a product",
            description = "Returns the applicable price for a product at a specific date"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Price found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PriceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Price not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameters",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })

    @GetMapping
    public Mono<ResponseEntity<PriceResponse>> getPrice(
            @Parameter(description = "Application date (ISO format)", required = true, example = "2020-06-14T10:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,

            @Parameter(description = "Product identifier", required = true, example = "35455")
            @RequestParam Long productId,

            @Parameter(description = "Brand identifier (1 = ZARA)", required = true, example = "1")
            @RequestParam Long brandId
    ) {
        validateParameters(applicationDate, productId, brandId);

        return getPriceUseCase.getPriceByDateProductAndBrand(applicationDate, productId, brandId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private void validateParameters(LocalDateTime applicationDate, Long productId, Long brandId) {
        if (applicationDate == null) {
            throw new ServerWebInputException("Application date is required");
        }
        if (productId == null || productId <= 0) {
            throw new ServerWebInputException("Invalid product ID");
        }
        if (brandId == null || brandId <= 0) {
            throw new ServerWebInputException("Invalid brand ID");
        }
    }
}
