package com.apixandru.games.rummikub.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
class Main {

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(50122);
        new RummikubServer(serverSocket);

    }

}
