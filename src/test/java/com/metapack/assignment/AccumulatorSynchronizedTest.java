package com.metapack.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class AccumulatorSynchronizedTest {

    private Accumulator accumulator;

    @BeforeEach
    void init() {
        accumulator = new AccumulatorSynchronized();
    }

    @Test
    void Should_Return_TotalOf300000_When_Accumulate_HundredThreads_ThousandTimes_ThreeOnes() throws InterruptedException {
        // GIVEN
        int THREAD_COUNT = 100;
        int ACCUMULATION_COUNT = 1_000;
        int TOTAL_EXPECTED = THREAD_COUNT * ACCUMULATION_COUNT * 3;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        Runnable threadTask = () -> {
            for (int i = 0; i < ACCUMULATION_COUNT; i++) {
                accumulator.accumulate(1, 1, 1);
            }
            latch.countDown();
        };

        // WHEN
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(threadTask);
        }
        latch.await();
        executor.shutdown();

        // THEN
        assertEquals(TOTAL_EXPECTED, accumulator.getTotal());
    }
}
