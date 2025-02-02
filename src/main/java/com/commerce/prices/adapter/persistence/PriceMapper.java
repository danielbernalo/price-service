package com.commerce.prices.adapter.persistence;

import com.commerce.prices.application.dto.PriceResponse;
import com.commerce.prices.domain.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    private PriceMapper() {
    }
    public static PriceResponse toPriceResponse(Price price) {
        return new PriceResponse(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice()
        );
    }

    public static Price toDomain(PriceEntity priceEntity) {
        return Price.builder()
                .productId(priceEntity.getProductId())
                .brandId(priceEntity.getBrandId())
                .priceList(priceEntity.getPriceList())
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .price(priceEntity.getPrice())
                .build();
    }
}
