package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.RummikubSerializer;
import com.apixandru.rummikub.brotocol.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.apixandru.rummikub.connection.util.Util.closeQuietly;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public class SocketPacketConnection implements PacketConnection {

    private final Serializer serializer = new RummikubSerializer();

    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;

    public SocketPacketConnection(Socket socket) throws IOException {
        this.socket = socket;

        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.out.flush();

        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

    }

    @Override
    public void writePacket(Packet packet) {
        try {
            synchronized (out) {
                serializer.serialize(packet, out);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to send packet", e);
        }
    }

    @Override
    public Packet readPacket() throws IOException {
        return serializer.deserialize(this.in);
    }

    @Override
    public void close() {
        closeQuietly(in, out, socket);
    }

}
