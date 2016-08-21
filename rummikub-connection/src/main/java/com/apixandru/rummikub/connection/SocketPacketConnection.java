package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.RummikubSerializer;
import com.apixandru.rummikub.brotocol.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(SocketPacketConnection.class);

    private final Serializer serializer = new RummikubSerializer();

    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;

    private boolean closed;

    public SocketPacketConnection(Socket socket) throws IOException {
        this.socket = socket;

        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.out.flush();

        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

    }

    @Override
    public boolean trySendPacket(Packet packet) {
        if (closed) {
            return false;
        }
        try {
            synchronized (out) {
                serializer.serialize(packet, out);
            }
            return true;
        } catch (IOException e) {
            log.error("Failed to send packet", e);
            close();
            return false;
        }
    }

    @Override
    public Packet readPacket() throws ConnectionLostException {
        ensureNotClosed();
        try {
            return serializer.deserialize(this.in);
        } catch (IOException e) {
            close();
            throw new ConnectionLostException("Failed to read packet", e);
        }
    }

    @Override
    public void close() {
        closed = true;
        closeQuietly(in, out, socket);
    }

    private void ensureNotClosed() throws ConnectionLostException {
        if (closed) {
            throw new ConnectionLostException("Connection closed");
        }
    }

}
