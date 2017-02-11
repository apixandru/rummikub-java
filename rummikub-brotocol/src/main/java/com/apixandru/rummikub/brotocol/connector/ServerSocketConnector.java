package com.apixandru.rummikub.brotocol.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public class ServerSocketConnector implements Connector {

    private final ServerSocket serverSocket;

    public ServerSocketConnector(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public static ServerSocketConnector randomPortServerSocketConnector() throws IOException {
        return new ServerSocketConnector(0);
    }

    public void setSocketTimeout(long duration, TimeUnit timeUnit) throws SocketException {
        this.serverSocket.setSoTimeout((int) timeUnit.toMillis(duration));
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