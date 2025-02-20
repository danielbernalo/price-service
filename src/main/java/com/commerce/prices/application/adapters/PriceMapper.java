package com.commerce.prices.application.adapters;

import com.commerce.prices.application.dto.PriceResponse;
import com.commerce.prices.domain.Price;

public class PriceMapper {
    private PriceMapper() {
    }

    public static PriceResponse toPriceResponse(Price price) {
        return PriceResponse.builder()
                .productId(price.getId().getProductId())
                .brandId(price.getId().getBrandId())
                .startDate(price.getDateRange().getStartDate())
                .endDate(price.getDateRange().getEndDate())
                .priceList(price.getPriceList())
                .price(price.getPrice().getAmount())
                .currency(price.getPrice().getCurrency().getCode())
                .build();
    }
}
