package com.ofg.loans.utils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhabba on 18.03.16.
 */
public class Utils {
    private static final long startId = new Date().getTime();
    private static final AtomicLong counter = new AtomicLong(0);

    public static long getNextId() {
        long num = counter.getAndIncrement();
        return startId + num;
    }
}
