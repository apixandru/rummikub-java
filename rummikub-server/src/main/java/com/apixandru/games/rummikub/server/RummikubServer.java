package com.apixandru.games.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.server.ConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
class RummikubServer {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(50122);

        log.debug("Listening on port {}", serverSocket.getLocalPort());

        final ConnectionHandler joiner = new ConnectionHandler();
        while (true) {
            log.debug("Waiting for client...");
            joiner.attemptToJoin(new SocketWrapper(serverSocket.accept()));
        }

    }

}
