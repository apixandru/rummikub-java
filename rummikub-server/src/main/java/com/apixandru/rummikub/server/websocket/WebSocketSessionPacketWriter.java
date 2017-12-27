package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public final class WebSocketSessionPacketWriter implements PacketWriter {

    private final Gson gson = new Gson();

    private final WebSocketSession session;

    public WebSocketSessionPacketWriter(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void writePacket(Packet packet) {
        String json = gson.toJson(packet);
        String packetClass = packet.getClass().getName();
        TextMessage message = new TextMessage(packetClass + " " + json);
        try {
            session.sendMessage(message);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Can't serialize " + packetClass);
        }
    }

    @Override
    public void close() throws IOException {
        session.close();
    }

}
