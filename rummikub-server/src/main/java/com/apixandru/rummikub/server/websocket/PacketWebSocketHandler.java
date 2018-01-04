package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.websocket.JsonBrotocol;
import com.apixandru.rummikub.server.TidyPacketHandler;
import com.apixandru.rummikub.server.TidyPacketHandlerAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public abstract class PacketWebSocketHandler extends TextWebSocketHandler implements TidyPacketHandlerAware {

    private static final Logger log = LoggerFactory.getLogger(PacketWebSocketHandler.class);

    protected final JsonBrotocol brotocol = new JsonBrotocol();
    private TidyPacketHandler packetHandler;

    protected final void send(WebSocketSession session, Packet packet) throws IOException {
        String encodedPacket = brotocol.encode(packet);
        session.sendMessage(new TextMessage(encodedPacket));
    }

    private void close(WebSocketSession session, int code, String message) throws IOException {
        session.close(new CloseStatus(code, message));
    }

    @Override
    protected final void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("Received message: {}", payload);
        if (!brotocol.willDecode(payload)) {
            close(session, 4009, "Won't decode this!");
            return;
        }
        Packet packet = brotocol.decode(payload);
        handlePacket(session, packet);
    }

    protected abstract void handlePacket(WebSocketSession session, Packet packet) throws IOException;

    @Override
    public void setPacketHandler(TidyPacketHandler packetHandler) {
        cleanup();
        this.packetHandler = packetHandler;
    }

    private void cleanup() {
        if (null != this.packetHandler) {
            this.packetHandler.cleanup();
        }
    }

    protected final void handlePacket(Packet packet) {
        this.packetHandler.handle(packet);
    }

}
