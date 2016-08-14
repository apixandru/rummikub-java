package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol2.Connector;
import com.apixandru.rummikub.brotocol2.ServerSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public class Main {

    static final int DEFAULT_PORT = 50122;

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        int port = getPort(args);
        ServerSocket serverSocket = new ServerSocket(port);
        Connector connector = new ServerSocketConnector(serverSocket);
        new RummikubServerThread(connector).start();
    }

    static int getPort(String... args) {
        if (args.length > 0) {
            Integer port = parseInt(args[0]);
            if (null != port) {
                return port;
            }
        }
        log.debug("No port found, defaulting to {}", DEFAULT_PORT);
        return DEFAULT_PORT;
    }

    private static Integer parseInt(String string) {
        try {
            int intValue = Integer.parseInt(string);
            if (String.valueOf(intValue).equals(string)) {
                return intValue;
            }
        } catch (NumberFormatException ex) {
            log.warn("Cannot parse {}", string, ex);
        }
        return null;
    }

}
