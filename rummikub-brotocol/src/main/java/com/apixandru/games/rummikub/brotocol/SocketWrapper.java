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
public final class SocketWrapper implements BroReader, BroWriter, PacketWriter, PacketReader {

    private final Serializer serializer = new RummikubSerializer();

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    /**
     * @param socket the socket to wrap
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
    public boolean readBoolean() throws IOException {
        return this.in.readInt() == 1;
    }

    @Override
    public String readString() throws IOException {
        final int length = readInt();
        final StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) readInt());
        }
        return sb.toString();
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
    public void write(final String string) {
        write(string.length());
        for (char c : string.toCharArray()) {
            write((int) c);
        }
    }

    @Override
    public void writePacket(final Packet packet) {
        try {
            synchronized (out) {
                serializer.serialize(packet, out);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public synchronized Packet readPacket() throws IOException {
        synchronized (in) {
            return serializer.deserialize(in);
        }
    }

    @Override
    public void flush() {
        try {
            this.out.flush();
        } catch (IOException e) {
//            deez exceptions happen
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.socket.close();
    }

}
