package com.metapack.assignment;

public class AccumulatorSynchronized extends AccumulatorSequential {

    @Override
    public synchronized int accumulate(int... values) {
        return super.accumulate(values);
    }

    @Override
    public synchronized void reset() {
        super.reset();
    }
}
