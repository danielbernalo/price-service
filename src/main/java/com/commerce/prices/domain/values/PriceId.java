package com.commerce.prices.domain.values;


import com.commerce.prices.domain.common.ValueObject;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PriceId implements ValueObject {
    private final Long brandId;
    private final Long productId;
    private final Currency currency;

    private PriceId(Long brandId, Long productId, Currency currency) {
        this.brandId = Objects.requireNonNull(brandId, "Brand ID cannot be null");
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.currency =  validateCurrency(currency);

        validate();
    }

    private Currency validateCurrency(final Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        return currency;
    }

    public static PriceId of(Long brandId, Long productId, String currency) {
        return new PriceId(brandId, productId, Currency.of(currency));
    }

    private void validate() {
        if (brandId <= 0) {
            throw new IllegalArgumentException("Brand ID must be positive");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceId priceId = (PriceId) o;
        return brandId.equals(priceId.brandId) &&
                productId.equals(priceId.productId) &&
                currency.equals(priceId.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, productId, currency);
    }

    @Override
    public String toString() {
        return String.format("PriceId{brandId=%d, productId=%d, currency='%s'}",
                brandId, productId, currency);
    }
}