package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.game.client.LoginRequest;
import com.apixandru.rummikub.brotocol.game.server.LoginResponse;
import com.apixandru.rummikub.server.RummikubException;
import com.apixandru.rummikub.server.RummikubImpl;
import com.apixandru.rummikub.server.ServerStateChangeListener;
import org.slf4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MyWebSocketHandler extends PacketWebSocketHandler {

    private final Logger log = getLogger(this.getClass().getName());

    private final RummikubImpl rummikub = new RummikubImpl();

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final Map<String, String> users = new HashMap<>();

    private static void send(WebSocketSession session, String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("{} connected.");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("{} disconnected.");

        String username = users.get(session.getId());

        if (username != null) {
            rummikub.unregister(username);
        }
    }

    @Override
    protected void handlePacket(WebSocketSession session, Packet packet) throws IOException {
        if (packet instanceof LoginRequest) {
            doLogin(((LoginRequest) packet).playerName, session);
            return;
        }
        handlePacket(packet);
    }

    private void doLogin(String playerName, WebSocketSession session) throws IOException {
        LoginResponse response = new LoginResponse();
        try {
            rummikub.validateCanJoin(playerName);
            response.accepted = true;
            send(session, response);
            WebSocketSessionPacketWriter writer = new WebSocketSessionPacketWriter(session);
            ServerStateChangeListener stateChangeListener = new ServerStateChangeListener(playerName, writer, this);
            rummikub.addPlayer(playerName, stateChangeListener);
            users.put(session.getId(), playerName);
        } catch (RummikubException ex) {
            response.accepted = false;
            response.reason = "Username already taken.";
            send(session, response);
        }
    }

}
