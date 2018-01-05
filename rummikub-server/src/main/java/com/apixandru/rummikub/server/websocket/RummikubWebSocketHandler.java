package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.game.client.LoginRequest;
import com.apixandru.rummikub.brotocol.game.server.LoginResponse;
import com.apixandru.rummikub.server.RummikubException;
import com.apixandru.rummikub.server.RummikubImpl;
import com.apixandru.rummikub.server.ServerStateChangeListener;
import org.slf4j.Logger;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

final class RummikubWebSocketHandler extends PacketWebSocketHandler {

    private static final Logger log = getLogger(RummikubWebSocketHandler.class);

    private final RummikubImpl rummikub = new RummikubImpl();

    @Override
    protected void afterConnectionEstablished(UserSession session) {
        log.info("{} connected.", session);
    }

    @Override
    public void afterConnectionClosed(UserSession session, CloseStatus status) {
        log.info("{} disconnected.", session);

        String username = session.getPlayerName();
        if (username != null) {
            rummikub.unregister(username);
        }
    }

    @Override
    protected void handlePacket(UserSession session, Packet packet) throws IOException {
        if (packet instanceof LoginRequest) {
            doLogin(((LoginRequest) packet).playerName, session);
            return;
        }
        session.handle(packet);
    }

    private void doLogin(String playerName, UserSession session) throws IOException {
        LoginResponse response = new LoginResponse();
        try {
            rummikub.validateCanJoin(playerName);
            response.playerName = playerName;
            response.accepted = true;
            session.writePacket(response);
            ServerStateChangeListener stateChangeListener = new ServerStateChangeListener(playerName, session, session);
            rummikub.addPlayer(playerName, stateChangeListener);
            session.setPlayerName(playerName);
            log.info("Connection established.");
        } catch (RummikubException ex) {
            response.accepted = false;
            response.reason = "Username already taken.";
            session.writePacket(response);
        }
    }

}
