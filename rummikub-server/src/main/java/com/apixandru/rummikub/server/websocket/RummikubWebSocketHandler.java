package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.server.RummikubImpl;
import com.apixandru.rummikub.server.login.LoginPacketHandler;
import org.slf4j.Logger;
import org.springframework.web.socket.CloseStatus;

import static org.slf4j.LoggerFactory.getLogger;

final class RummikubWebSocketHandler extends PacketWebSocketHandler {

    private static final Logger log = getLogger(RummikubWebSocketHandler.class);

    private final RummikubImpl rummikub = new RummikubImpl();

    @Override
    protected void afterConnectionEstablished(UserSession session) {
        log.info("{} connected.", session);
        new LoginPacketHandler(rummikub, session)
                .handleLoginRequest(session);
    }

    @Override
    public void afterConnectionClosed(UserSession session, CloseStatus status) {
        log.info("{} disconnected.", session);

        String username = session.getPlayerName();
        if (username != null) {
            rummikub.unregister(username);
        }
        session.cleanup();
    }

}
