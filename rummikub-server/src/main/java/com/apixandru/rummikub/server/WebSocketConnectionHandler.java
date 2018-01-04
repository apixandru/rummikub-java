package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.game.client.LoginRequest;
import com.apixandru.rummikub.brotocol.game.server.LoginResponse;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
class WebSocketConnectionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketConnectionHandler.class);

    private final RummikubImpl rummikub = new RummikubImpl();

    private static void reject(final PacketWriter packetWriter, final Exception exception) {
        LoginResponse response = new LoginResponse();
        response.reason = exception.getMessage();
        packetWriter.writePacket(response);
        log.debug("Rejected: {}", exception.getMessage());
    }

    synchronized void attemptToJoin(final SocketWrapper wrapper) {
        try {
            LoginRequest packet = (LoginRequest) wrapper.readPacket();
            final String playerName = packet.playerName;
            log.debug("{} is attempting to join.", playerName);
            accept(wrapper, playerName);
        } catch (final RummikubException | IOException ex) {
            reject(wrapper, ex);
        }
    }

    private void accept(final SocketWrapper wrapper, final String playerName) {
        rummikub.validateCanJoin(playerName);
        LoginResponse response = new LoginResponse();
        response.accepted = true;
        wrapper.writePacket(response);
        ConnectionListener connectionListener = () -> rummikub.unregister(playerName);
        SocketPacketProcessor processor = new SocketPacketProcessor(wrapper, connectionListener);
        ServerStateChangeListener stateChangeListener = new ServerStateChangeListener(playerName, wrapper, processor);
        rummikub.addPlayer(playerName, stateChangeListener);
        log.debug("Accepted.");
        new Thread(processor, playerName).start();
    }

}
