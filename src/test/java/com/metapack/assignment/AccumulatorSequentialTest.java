package com.metapack.assignment;

import com.metapack.assignment.exception.AccumulationExceedsLimitException;
import com.metapack.assignment.exception.IncorrectAccumulationComponentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccumulatorSequentialTest {

    private Accumulator accumulator;

    @BeforeEach
    void init() {
        accumulator = new AccumulatorSequential();
    }

    /**
     * Accumulation results
     */
    @Test
    void Should_Return_Zero_When_Accumulate_Empty() {
        int accumulated = accumulator.accumulate();

        assertEquals(0, accumulated);
    }

    @Test
    void Should_Return_Zero_When_Accumulate_Zero() {
        int accumulated = accumulator.accumulate(0);

        assertEquals(0, accumulated);
    }

    @Test
    void Should_Return_Negative_When_Accumulate_SingleNegative() {
        int accumulated = accumulator.accumulate(-5);

        assertEquals(-5, accumulated);
    }

    @Test
    void Should_Return_Negative_When_Accumulate_SinglePositive() {
        int accumulated = accumulator.accumulate(2137);

        assertEquals(2137, accumulated);
    }

    @Test
    void Should_Return_Ten_When_Accumulate_FiveAndFive() {
        int accumulated = accumulator.accumulate(5, 5);

        assertEquals(10, accumulated);
    }

    @Test
    void Should_Return_MinusTen_When_Accumulate_MinusFives() {
        int accumulated = accumulator.accumulate(-5, -5);

        assertEquals(-10, accumulated);
    }

    @Test
    void Should_Return_Zero_When_Accumulate_FiveAndMinusFive() {
        int accumulated = accumulator.accumulate(5, -5);

        assertEquals(0, accumulated);
    }

    @Test
    void Should_Return_MaxInt_When_Accumulated_Is_MaxInt() {
        int accumulated = accumulator.accumulate(2147483646, 1);

        assertEquals(Integer.MAX_VALUE, accumulated);
    }

    @Test
    void Should_Return_MinInt_When_Accumulated_Is_MinInt() {
        int accumulated = accumulator.accumulate(-2147483647, -1);

        assertEquals(Integer.MIN_VALUE, accumulated);
    }

    @Test
    void Should_Return_Accumulated_Arguments_When_Accumulate_Multiple_Times() {
        accumulator.accumulate(1, 1);
        accumulator.accumulate(2, 2);
        accumulator.accumulate(3, 3);
        accumulator.accumulate(4, 4);

        int accumulated = accumulator.accumulate(5, 5);

        assertEquals(10, accumulated);
    }


    /**
     * Total sum results
     */

    @Test
    void Should_GetTotalOfTen_When_Accumulate_FiveAndFive() {
        accumulator.accumulate(5, 5);

        int total = accumulator.getTotal();

        assertEquals(10, total);
    }

    @Test
    void Should_GetTotalOfMinusTen_When_Accumulate_MinusFives() {
        accumulator.accumulate(-5, -5);

        int total = accumulator.getTotal();

        assertEquals(-10, total);
    }

    @Test
    void Should_GetTotalOfZero_When_Accumulate_FiveAndMinusFive() {
        accumulator.accumulate(5, -5);

        int total = accumulator.getTotal();

        assertEquals(0, total);
    }

    @Test
    void Should_GetTotalOfTwenty_When_Accumulate_FourTimesFive() {
        accumulator.accumulate(5);
        accumulator.accumulate(5);
        accumulator.accumulate(5);
        accumulator.accumulate(5);

        int total = accumulator.getTotal();

        assertEquals(20, total);
    }

    @Test
    void Should_GetTotalOfZero_When_Accumulate_FivesAndMinusFives() {
        accumulator.accumulate(5);
        accumulator.accumulate(5);
        accumulator.accumulate(-5);
        accumulator.accumulate(-5);

        int total = accumulator.getTotal();

        assertEquals(0, total);
    }

    @Test
    void Should_GetTotalOfMaxInt_When_Accumulate_To_MaxInt() {
        accumulator.accumulate(2147483646, 1);

        int total = accumulator.getTotal();

        assertEquals(Integer.MAX_VALUE, total);
    }

    @Test
    void Should_GetTotalOfMinInt_When_Accumulate_To_MinInt() {
        accumulator.accumulate(-2147483647, -1);

        int total = accumulator.getTotal();

        assertEquals(Integer.MIN_VALUE, total);
    }

    @Test
    void Should_GetTotalOfMaxInt_When_Accumulate_Multiple_Times_To_MaxInt() {
        accumulator.accumulate(2147483643, 1);
        accumulator.accumulate(1);
        accumulator.accumulate(1);
        accumulator.accumulate(1);

        int total = accumulator.getTotal();

        assertEquals(Integer.MAX_VALUE, total);
    }

    @Test
    void Should_GetTotalOfMinInt_When_Accumulate_Multiple_Times_To_MinInt() {
        accumulator.accumulate(-2147483644, -1);
        accumulator.accumulate(-1);
        accumulator.accumulate(-1);
        accumulator.accumulate(-1);

        int total = accumulator.getTotal();

        assertEquals(Integer.MIN_VALUE, total);
    }

    /**
     * Reset
     */

    @Test
    void Should_ResetSum_To_Zero() {
        accumulator.accumulate(5, 5);
        accumulator.accumulate(5);
        accumulator.accumulate(5);
        accumulator.accumulate(5);
        assertEquals(25, accumulator.getTotal());

        accumulator.reset();

        assertEquals(0, accumulator.getTotal());
    }

    @Test
    void Should_Return_Ten_When_Reset_And_Accumulate_FiveAndFive() {
        accumulator.accumulate(5, 5);
        accumulator.accumulate(5);
        accumulator.accumulate(5);
        accumulator.accumulate(5);
        assertEquals(25, accumulator.getTotal());

        accumulator.reset();
        accumulator.accumulate(5, 5);

        assertEquals(10, accumulator.getTotal());
    }

    /**
     * Exceptions
     */

    @Test
    void Should_Throw_IncorrectAccumulationComponentException_When_Passing_Null() {
        Exception exception = assertThrows(IncorrectAccumulationComponentException.class, () -> accumulator.accumulate(null));

        String expectedMessage = AccumulatorSequential.ARGUMENT_NULL_EXCEPTION_MESSAGE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void Should_Throw_AccumulationExceedsLimitException_When_Accumulation_OverflowsInteger() {
        Exception exception = assertThrows(AccumulationExceedsLimitException.class, () -> accumulator.accumulate(2147483646, 1, 1));

        String expectedMessage = AccumulatorSequential.SUM_EXCEEDS_THE_LIMIT_EXCEPTION_MESSAGE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void Should_Throw_AccumulationExceedsLimitException_When_Total_OverflowsInteger() {
        accumulator.accumulate(2147483646);
        accumulator.accumulate(1);

        Exception exception = assertThrows(AccumulationExceedsLimitException.class, () -> accumulator.accumulate(1));

        String expectedMessage = AccumulatorSequential.SUM_EXCEEDS_THE_LIMIT_EXCEPTION_MESSAGE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
