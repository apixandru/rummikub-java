package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.websocket.JsonSerializer;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public final class UserSession implements PacketWriter {

    private final JsonSerializer serializer = new JsonSerializer();

    private final WebSocketSession session;

    private String playerName;

    public UserSession(WebSocketSession session) {
        this.session = session;
    }

    public String getId() {
        return session.getId();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void writePacket(Packet packet) {
        TextMessage message = new TextMessage(serializer.encode(packet));
        try {
            session.sendMessage(message);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Can't serialize " + packet, ex);
        }
    }

    @Override
    public void close() throws IOException {
        session.close();
    }

    @Override
    public String toString() {
        return "[sid=" + getId() + ", playerName=" + playerName + "]";
    }

}
