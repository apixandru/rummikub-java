package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol2.ServerSocketConnector;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public class RummikubServerThreadTest {

    @Test(timeout = 5000)
    public void testStop() throws IOException, InterruptedException {
        ServerSocketConnector connector = new ServerSocketConnector(new ServerSocket(0));
        connector.setSocketTimeout(500, MILLISECONDS);
        RummikubServerThread thread = new RummikubServerThread(connector);
        thread.start();

        MILLISECONDS.sleep(100);

        thread.stopListening();

        SECONDS.sleep(1);

        thread.join();
    }

}
