package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.connector.ServerSocketConnector;
import org.junit.Test;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public class RummikubServerThreadTest {

    @Test(timeout = 5000)
    public void testStop() throws IOException, InterruptedException {
        ServerSocketConnector connector = ServerSocketConnector.randomPortServerSocketConnector();
        connector.setSocketTimeout(500, MILLISECONDS);
        RummikubServerThread thread = new RummikubServerThread(connector);
        thread.start();

        MILLISECONDS.sleep(100);

        thread.shutdown();

        thread.join();
    }

}
