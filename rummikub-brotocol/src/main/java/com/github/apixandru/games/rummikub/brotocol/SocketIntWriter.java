package com.github.apixandru.games.rummikub.brotocol;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class SocketIntWriter implements IntWriter {

    private final Socket socket;
    private final ObjectOutputStream out;

    /**
     * @param socket
     */
    public SocketIntWriter(final Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        flush(); // send out the object stream header
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
