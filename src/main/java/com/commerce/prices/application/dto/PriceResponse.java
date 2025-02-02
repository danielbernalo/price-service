package com.commerce.prices.application.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class PriceResponse {
    private final Long productId;
    private final Long brandId;
    private final Long priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final BigDecimal price;

}
