package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.websocket.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

abstract class PacketWebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(PacketWebSocketHandler.class);

    private final JsonSerializer serializer = new JsonSerializer();

    private final Map<String, UserSession> sessionMap = new HashMap<>();

    @Autowired
    private SimpMessagingTemplate simp;

    @Override
    public final void afterConnectionEstablished(WebSocketSession session) {
//        UserSession userSession = new UserSession(session, simp);
//        sessionMap.put(session.getId(), userSession);
//        afterConnectionEstablished(userSession);
    }

    @Override
    public final void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UserSession userSession = getUserSession(session);
        afterConnectionClosed(userSession, status);
    }

    @Override
    protected final void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        handleTextMessage(getUserSession(session), message.getPayload());
    }

    protected final void handleTextMessage(UserSession userSession, String payload) throws IOException {
        log.info("Received message: {}", payload);
        if (!serializer.willDecode(payload)) {
            log.warn("Unrecognised message, get out of here!");
            userSession.close(4009, "Won't decode this!");
            return;
        }
        Packet packet = serializer.decode(payload);
        userSession.handle(packet);
    }

    protected final void handleTextMessage(String username, String payload) throws IOException {
        handleTextMessage(getUserSession(username), payload);
    }

    private UserSession getUserSession(String username) {
        return sessionMap.values()
                .stream()
                .filter(session -> session.getPlayerName().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No user " + username));
    }

    private UserSession getUserSession(WebSocketSession session) {
        return sessionMap.get(session.getId());
    }

    public abstract void afterConnectionClosed(UserSession session, CloseStatus status);

    protected abstract void afterConnectionEstablished(UserSession session);

    @EventListener
    public void handleConnect(SessionSubscribeEvent event) {
        Principal user = event.getUser();
        UserSession userSession = new UserSession(user.getName(), simp);
        sessionMap.put(userSession.getId(), userSession);
        afterConnectionEstablished(userSession);
    }
}
