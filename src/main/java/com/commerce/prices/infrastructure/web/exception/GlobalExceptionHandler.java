package com.commerce.prices.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handlePriceNotFoundException(
            PriceNotFoundException ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(buildErrorResponse(
                ex.getMessage(),
                "PRICE_NOT_FOUND",
                exchange,
                HttpStatus.NOT_FOUND
        ));
    }

    @ExceptionHandler({
            MissingRequestValueException.class,
            InvalidPriceRequestException.class,
            IllegalArgumentException.class,
            ServerWebInputException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidPriceRequestException(
            RuntimeException ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(buildErrorResponse(
                ex.getMessage(),
                "INVALID_REQUEST",
                exchange,
                HttpStatus.BAD_REQUEST
        ));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleAllUncaughtException(
            Exception ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(buildErrorResponse(
                "An unexpected error occurred",
                "INTERNAL_SERVER_ERROR",
                exchange,
                HttpStatus.INTERNAL_SERVER_ERROR
        ));
    }

    private ErrorResponse buildErrorResponse(
            String message,
            String code,
            ServerWebExchange exchange,
            HttpStatus status
    ) {
        return ErrorResponse.builder()
                .message(message)
                .code(code)
                .path(exchange.getRequest().getPath().value())
                .timestamp(LocalDateTime.now())
                .method(exchange.getRequest().getMethod().name())
                .status(status.value())                .build();
    }
}