package com.apixandru.rummikub.server.login;

import com.apixandru.rummikub.brotocol.game.client.LoginRequest;
import com.apixandru.rummikub.brotocol.game.server.LoginResponse;
import com.apixandru.rummikub.brotocol.util.AbstractMultiPacketHandler;
import com.apixandru.rummikub.server.RummikubException;
import com.apixandru.rummikub.server.RummikubImpl;
import com.apixandru.rummikub.server.ServerStateChangeListener;
import com.apixandru.rummikub.server.websocket.UserSession;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public final class LoginPacketHandler extends AbstractMultiPacketHandler {

    private static final Logger log = getLogger(LoginPacketHandler.class);

    private final RummikubImpl rummikub;
    private final UserSession session;

    public LoginPacketHandler(RummikubImpl rummikub, UserSession session) {
        this.rummikub = rummikub;
        this.session = session;

        register(LoginRequest.class, this::handleLoginRequest);
    }

    private void handleLoginRequest(LoginRequest packet) {
        String playerName = packet.playerName;
        try {
            accept(playerName);
            ServerStateChangeListener stateChangeListener = new ServerStateChangeListener(playerName, session, session);
            rummikub.addPlayer(playerName, stateChangeListener);
            log.info("Connection established.");
        } catch (RummikubException ex) {
            reject(ex.getMessage());
        }
    }

    private void accept(String playerName) {
        LoginResponse response = new LoginResponse();
        rummikub.validateCanJoin(playerName);
        response.playerName = playerName;
        response.accepted = true;
        session.writePacket(response);
    }

    private void reject(String message) {
        LoginResponse response = new LoginResponse();
        response.accepted = false;
        response.reason = message;
        session.writePacket(response);
    }

}
