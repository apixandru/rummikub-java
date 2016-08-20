package com.apixandru.rummikub.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public final class TestUtils {

    private static final Logger log = LoggerFactory.getLogger(TestUtils.class);

    private TestUtils() {
    }

    public static Thread launchThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    public static void awaitCompletion(Thread thread, int time, TimeUnit timeUnit) {
        try {
            thread.join(timeUnit.toMillis(time));
        } catch (InterruptedException e) {
            log.debug("Interrupted, no big deal", e);
        }
        assertFalse("Expecting thread to be dead", thread.isAlive());
    }

    public static void safeSleep(int time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            log.debug("Interrupted, no big deal", e);
        }
    }

}
