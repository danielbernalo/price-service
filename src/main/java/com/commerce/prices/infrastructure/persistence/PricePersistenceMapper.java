package com.commerce.prices.infrastructure.persistence;

import com.commerce.prices.domain.Price;
import com.commerce.prices.domain.values.DateRange;
import com.commerce.prices.domain.values.Money;
import com.commerce.prices.domain.values.PriceId;
import org.springframework.stereotype.Component;

@Component
public class PricePersistenceMapper {

    public Price toDomain(PriceEntity entity) {
        return new Price.Builder()
                .id(PriceId.of(entity.getBrandId(), entity.getProductId(), entity.getCurrency()))
                .dateRange(DateRange.of(entity.getStartDate(), entity.getEndDate()))
                .priceList(entity.getPriceList())
                .priority(entity.getPriority())
                .price(Money.of(entity.getPrice(), entity.getCurrency()))
                .build();
    }
}