package com.apixandru.rummikub.brotocol2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public final class SocketConnection implements Connection {

    private final Socket socket;

    private final DataOutputStream out;
    private final DataInputStream in;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;

        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.out.flush(); // send out the stream header

        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public DataOutputStream getDataOutputStream() {
        return out;
    }

    @Override
    public DataInputStream getDataInputStream() {
        return in;
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
    }

}
