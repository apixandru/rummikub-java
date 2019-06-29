package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.brotocol.util.PacketHandlerAware;
import com.apixandru.rummikub.brotocol.websocket.JsonSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public final class UserSession implements PacketWriter, PacketHandlerAware, PacketHandler<Packet> {

    private final static AtomicLong idGenerator = new AtomicLong();

    private final JsonSerializer serializer = new JsonSerializer();
    private final String id;
    private final String username;

//    private final WebSocketSession session;

    private MultiPacketHandler packetHandler;

    private SimpMessagingTemplate messaging;

    public UserSession(String username, SimpMessagingTemplate messaging) {
//        this.session = session;
        this.username = username;
        this.messaging = messaging;
        this.id = String.valueOf(idGenerator.getAndIncrement());
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return username;
    }

    @Override
    public void writePacket(Packet packet) {
        String encodedMessage = serializer.encode(packet);
        TextMessage message = new TextMessage(encodedMessage);
//        try {
//            session.sendMessage(message);
        messaging.convertAndSendToUser(getPlayerName(), "/queue/notifications", encodedMessage);

//        } catch (IOException ex) {
//            throw new IllegalArgumentException("Can't serialize " + packet, ex);
//        }
    }

    @Override
    public void close() throws IOException {
//        session.close();
    }

    public void close(int code, String message) throws IOException {
//        session.close(new CloseStatus(code, message));
    }

    @Override
    public String toString() {
        return "[sid=" + getId() + ", playerName=" + getPlayerName() + "]";
    }


    @Override
    public void setPacketHandler(MultiPacketHandler packetHandler) {
        cleanup(false);
        this.packetHandler = packetHandler;
    }

    public void cleanup(boolean force) {
        if (null != this.packetHandler) {
            this.packetHandler.cleanup(force);
        }
    }

    @Override
    public void handle(Packet packet) {
        this.packetHandler.handle(packet);
    }

}
