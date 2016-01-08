package com.apixandru.games.rummikub.brotocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class SocketWrapper implements IntReader, IntWriter {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    /**
     * @param socket
     */
    public SocketWrapper(final Socket socket) throws IOException {
        this.socket = socket;

        this.out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        flush(); // send out the object stream header

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

    @Override
    public void write(final int... ints) {
        try {
            for (int oneInt : ints) {
                this.out.writeInt(oneInt);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void flush() {
        try {
            this.out.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
