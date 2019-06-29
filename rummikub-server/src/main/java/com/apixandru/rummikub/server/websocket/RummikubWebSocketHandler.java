package com.apixandru.rummikub.server.websocket;

import com.apixandru.rummikub.server.RummikubImpl;
import com.apixandru.rummikub.server.login.LoginPacketHandler;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.security.Principal;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
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
        session.cleanup(true);
    }

    @EventListener
    public void handleDisconnet(SessionDisconnectEvent event) {
        System.out.println(event);
    }

    @MessageMapping("/doAction")
    public void handleMessage(Principal principal, String message) throws IOException {
        String username = principal.getName();
        handleTextMessage(username, message);
    }

}
