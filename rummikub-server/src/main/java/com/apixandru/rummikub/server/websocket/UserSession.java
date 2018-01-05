package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.brotocol.util.PacketHandlerAware;
import com.apixandru.rummikub.brotocol.websocket.JsonSerializer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public final class UserSession implements PacketWriter, PacketHandlerAware, PacketHandler<Packet> {

    private final JsonSerializer serializer = new JsonSerializer();

    private final WebSocketSession session;

    private String playerName;

    private MultiPacketHandler packetHandler;

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

    public void close(int code, String message) throws IOException {
        session.close(new CloseStatus(code, message));
    }

    @Override
    public String toString() {
        return "[sid=" + getId() + ", playerName=" + playerName + "]";
    }


    @Override
    public void setPacketHandler(MultiPacketHandler packetHandler) {
        cleanup();
        this.packetHandler = packetHandler;
    }

    private void cleanup() {
        if (null != this.packetHandler) {
            this.packetHandler.cleanup();
        }
    }

    @Override
    public void handle(Packet packet) {
        this.packetHandler.handle(packet);
    }

}
