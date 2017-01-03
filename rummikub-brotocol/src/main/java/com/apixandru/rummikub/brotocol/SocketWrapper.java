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
