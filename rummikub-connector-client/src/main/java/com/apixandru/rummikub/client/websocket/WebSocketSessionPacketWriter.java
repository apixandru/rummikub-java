package com.apixandru.rummikub.client.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketWriter;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public final class WebSocketSessionPacketWriter implements PacketWriter {

    private final Session session;

    public WebSocketSessionPacketWriter(Session session) {
        this.session = session;
    }

    @Override
    public void writePacket(Packet packet) {
        try {
            session.getBasicRemote()
                    .sendObject(packet);
        } catch (IOException | EncodeException ex) {
            throw new IllegalStateException("Failed to send packet.", ex);
        }
    }

    @Override
    public void close() throws IOException {
        session.close();
    }

}
