package com.commerce.prices.domain.values;


import com.commerce.prices.domain.exception.InvalidDateRangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {
    @Test
    void should_create_valid_date_range() {
        // given
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);

        // when
        DateRange range = DateRange.of(start, end);

        // then
        assertNotNull(range);
        assertEquals(start, range.getStartDate());
        assertEquals(end, range.getEndDate());
    }

    @Test
    void should_throw_exception_when_end_before_start() {
        // given
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusDays(1);

        // when/then
        assertThrows(InvalidDateRangeException.class,
                () -> DateRange.of(start, end));
    }

    @Test
    void should_throw_exception_when_dates_are_null() {
        assertThrows(NullPointerException.class,
                () -> DateRange.of(null, LocalDateTime.now()));
        assertThrows(NullPointerException.class,
                () -> DateRange.of(LocalDateTime.now(), null));
    }

    @ParameterizedTest
    @MethodSource("provideDatesForValidation")
    void should_validate_dates_correctly(DateTestCase testCase) {
        // given
        DateRange range = DateRange.of(testCase.rangeStart, testCase.rangeEnd);

        // when
        boolean isValid = range.isValidFor(testCase.testDate);

        // then
        assertEquals(testCase.expectedResult, isValid);
    }

    private static Stream<DateTestCase> provideDatesForValidation() {
        LocalDateTime now = LocalDateTime.now();
        return Stream.of(
                new DateTestCase(now, now.plusDays(2), now.plusDays(1), true),
                new DateTestCase(now, now.plusDays(2), now, true),
                new DateTestCase(now, now.plusDays(2), now.plusDays(2), true),
                new DateTestCase(now, now.plusDays(2), now.minusDays(1), false),
                new DateTestCase(now, now.plusDays(2), now.plusDays(3), false)
        );
    }

    @Test
    void should_detect_overlapping_ranges() {
        // given
        LocalDateTime now = LocalDateTime.now();
        DateRange range1 = DateRange.of(now, now.plusDays(5));

        // test various overlap scenarios
        assertTrue(range1.overlaps(DateRange.of(now.plusDays(2), now.plusDays(7))));
        assertTrue(range1.overlaps(DateRange.of(now.minusDays(2), now.plusDays(2))));
        assertTrue(range1.overlaps(DateRange.of(now.plusDays(1), now.plusDays(3))));
        assertFalse(range1.overlaps(DateRange.of(now.plusDays(6), now.plusDays(8))));
        assertFalse(range1.overlaps(DateRange.of(now.minusDays(5), now.minusDays(1))));
    }

    @Test
    void testEquals() {
        LocalDateTime now = LocalDateTime.now();
        DateRange range1 = DateRange.of(now, now.plusDays(1));
        DateRange range2 = DateRange.of(now, now.plusDays(1));
        DateRange range3 = DateRange.of(now, now.plusDays(2));

        assertEquals(range1, range2);
        assertNotEquals(range1, range3);
    }

    @Test
    void testHashCode() {
        LocalDateTime now = LocalDateTime.now();
        DateRange range1 = DateRange.of(now, now.plusDays(1));
        DateRange range2 = DateRange.of(now, now.plusDays(1));
        DateRange range3 = DateRange.of(now, now.plusDays(2));

        assertEquals(range1.hashCode(), range2.hashCode());
        assertNotEquals(range1.hashCode(), range3.hashCode());
    }

    private static class DateTestCase {
        LocalDateTime rangeStart;
        LocalDateTime rangeEnd;
        LocalDateTime testDate;
        boolean expectedResult;

        DateTestCase(LocalDateTime rangeStart, LocalDateTime rangeEnd,
                     LocalDateTime testDate, boolean expectedResult) {
            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
            this.testDate = testDate;
            this.expectedResult = expectedResult;
        }
    }
}