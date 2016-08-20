package com.apixandru.rummikub.connection;

import org.junit.Test;

import static com.apixandru.rummikub.connection.TestUtils.awaitCompletion;
import static com.apixandru.rummikub.connection.TestUtils.launchThread;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public class RummikubServerTest {

    @Test(timeout = 5000L)
    public void testShutdown() {
        RummikubServer rummikubServer = new RummikubServer(new MockPacketConnector());
        Thread thread = launchThread(rummikubServer);

        rummikubServer.shutdown();

        awaitCompletion(thread, 1, SECONDS);
    }


}
