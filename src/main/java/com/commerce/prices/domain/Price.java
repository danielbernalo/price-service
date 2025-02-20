package com.commerce.prices.domain;

import com.commerce.prices.domain.common.AggregateRoot;
import com.commerce.prices.domain.exception.InvalidPriorityException;
import com.commerce.prices.domain.values.Currency;
import com.commerce.prices.domain.values.DateRange;
import com.commerce.prices.domain.values.Money;
import com.commerce.prices.domain.values.PriceId;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Price implements AggregateRoot<PriceId> {
    private final PriceId id;
    private final DateRange dateRange;
    private final Long priceList;
    private final int priority;
    private final Money price;

    private Price(Builder builder) {
        this.id = builder.id;
        this.dateRange = Objects.requireNonNull(builder.dateRange, "Date range cannot be null");
        this.priceList = Objects.requireNonNull(builder.priceList, "Price list cannot be null");
        this.priority = validatePriority(builder.priority);
        this.price = Objects.requireNonNull(builder.price, "Price cannot be null");
    }

    @Override
    public PriceId getId() {
        return id;
    }

    private int validatePriority(int priority) {
        if (priority < 0) {
            throw new InvalidPriorityException("Priority cannot be negative");
        }
        return priority;
    }

    public boolean isApplicableAt(LocalDateTime date) {
        return dateRange.isValidFor(date);
    }

    public boolean hasPriorityOver(Price other) {
        return this.priority > other.priority;
    }

    // Builder
    public static class Builder {
        private PriceId id;
        private DateRange dateRange;
        private Long priceList;
        private int priority;
        private Money price;

        public Builder id(PriceId id) {
            this.id = id;
            return this;
        }

        public Builder dateRange(DateRange dateRange) {
            this.dateRange = dateRange;
            return this;
        }

        public Builder priceList(Long priceList) {
            this.priceList = priceList;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder price(Money price) {
            this.price = price;
            return this;
        }

        public Price build() {
            return new Price(this);
        }
    }
}