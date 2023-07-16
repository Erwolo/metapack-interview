package com.metapack.assignment;

import com.metapack.assignment.exception.AccumulationExceedsLimitException;
import com.metapack.assignment.exception.IncorrectAccumulationComponentException;

import java.util.Arrays;

public class AccumulatorSequential implements Accumulator {

    public static final String ARGUMENT_NULL_EXCEPTION_MESSAGE = "Argument can not be null";
    public static final String SUM_EXCEEDS_THE_LIMIT_EXCEPTION_MESSAGE = "Sum of arguments exceeds the limit";

    protected Integer total = 0;

    @Override
    public int accumulate(int... values) {
        if (values == null) {
            throw new IncorrectAccumulationComponentException(ARGUMENT_NULL_EXCEPTION_MESSAGE);
        }

        int accumulateValue = Arrays.stream(values).reduce(0, this::getSumValue);
        total = getSumValue(total, accumulateValue);

        return accumulateValue;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public void reset() {
        total = 0;
    }

    private int getSumValue(int subtotal, int element) {
        int tmpTotal;
        try {
            tmpTotal = Math.addExact(subtotal, element);
        } catch (ArithmeticException e) {
            throw new AccumulationExceedsLimitException(SUM_EXCEEDS_THE_LIMIT_EXCEPTION_MESSAGE, e);
        }

        return tmpTotal;
    }
}
