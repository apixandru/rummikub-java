package com.apixandru.rummikub.brotocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class SocketWrapper implements PacketWriter, PacketReader {

    private final Serializer serializer = new RummikubSerializer();

    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public SocketWrapper(final Socket socket) throws IOException {
        this.socket = socket;

        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.out.flush(); // send out the stream header

        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public String readString() throws IOException {
        return this.in.readUTF();
    }

    public void write(final String string) {
        try {
            this.out.writeUTF(string);
            this.out.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean readBoolean() throws IOException {
        return this.in.readBoolean();
    }

    public void write(final boolean value) {
        try {
            this.out.writeBoolean(value);
            this.out.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writePacket(final Packet packet) {
        try {
            synchronized (out) {
                serializer.serialize(packet, out);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to send packet", e);
        }
    }

    @Override
    public synchronized Packet readPacket() throws IOException {
        synchronized (in) {
            return serializer.deserialize(in);
        }
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.socket.close();
    }

}
