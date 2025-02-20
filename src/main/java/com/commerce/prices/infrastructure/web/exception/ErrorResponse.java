package com.commerce.prices.infrastructure.web.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String message,
        String code,
        String path,
        LocalDateTime timestamp,
        String method,
        Integer status
) {}
