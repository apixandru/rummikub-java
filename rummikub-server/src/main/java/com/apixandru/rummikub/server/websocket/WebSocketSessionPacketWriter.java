package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.websocket.JsonBrotocol;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public final class WebSocketSessionPacketWriter implements PacketWriter {

    private final JsonBrotocol brotocol = new JsonBrotocol();

    private final WebSocketSession session;

    public WebSocketSessionPacketWriter(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void writePacket(Packet packet) {
        TextMessage message = new TextMessage(brotocol.encode(packet));
        try {
            session.sendMessage(message);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Can't serialize " + packet);
        }
    }

    @Override
    public void close() throws IOException {
        session.close();
    }

}
