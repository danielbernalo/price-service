package com.commerce.prices.domain.common;

public interface ValueObject {
    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();
}
