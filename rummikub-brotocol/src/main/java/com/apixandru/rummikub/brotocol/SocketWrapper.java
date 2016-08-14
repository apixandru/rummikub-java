package com.apixandru.rummikub.brotocol;

import com.apixandru.rummikub.brotocol2.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class SocketWrapper implements PacketWriter, PacketReader {

    private final Serializer serializer = new RummikubSerializer();

    private final DataInputStream in;
    private final DataOutputStream out;
    private final Connection connection;

    public SocketWrapper(final Connection connection) {
        this.connection = connection;
        this.out = connection.getDataOutputStream();
        this.in = connection.getDataInputStream();
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
        this.connection.close();
    }

}
