package com.apixandru.mocksocket;

import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 15, 2016
 */
public final class MockSocketPair {

    private final Socket clientSide;
    private final Socket serverSide;

    public MockSocketPair(Socket clientSide, Socket serverSide) {
        this.clientSide = clientSide;
        this.serverSide = serverSide;
    }

    public Socket getClientSide() {
        return clientSide;
    }

    public Socket getServerSide() {
        return serverSide;
    }

}
