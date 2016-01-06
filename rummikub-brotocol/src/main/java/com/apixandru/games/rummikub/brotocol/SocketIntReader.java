package com.apixandru.games.rummikub.brotocol;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class SocketIntReader implements IntReader {

    private final Socket socket;
    private final ObjectInputStream in;

    /**
     * @param socket
     */
    public SocketIntReader(final Socket socket) throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public int readInt() throws IOException {
        return this.in.readInt();
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.socket.close();
    }

}
