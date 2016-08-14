package com.apixandru.rummikub.brotocol2;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public class ServerSocketConnector implements Connector {

    private final ServerSocket serverSocket;

    public ServerSocketConnector(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public Connection acceptConnection() throws IOException {
        return new SocketConnection(serverSocket.accept());
    }

    @Override
    public int getPort() {
        return serverSocket.getLocalPort();
    }

}
