package com.commerce.prices.domain.values;

import com.commerce.prices.domain.common.ValueObject;
import com.commerce.prices.domain.exception.InvalidDateRangeException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class DateRange implements ValueObject {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private DateRange(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = Objects.requireNonNull(startDate, "Start date cannot be null");
        this.endDate = Objects.requireNonNull(endDate, "End date cannot be null");

        if (endDate.isBefore(startDate)) {
            throw new InvalidDateRangeException("End date cannot be before start date");
        }
    }

    public static DateRange of(LocalDateTime startDate, LocalDateTime endDate) {
        return new DateRange(startDate, endDate);
    }

    public boolean isValidFor(LocalDateTime date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public boolean overlaps(DateRange other) {
        return !this.startDate.isAfter(other.endDate) &&
                !this.endDate.isBefore(other.startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange dateRange = (DateRange) o;
        return Objects.equals(startDate, dateRange.startDate) &&
                Objects.equals(endDate, dateRange.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}