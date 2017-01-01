package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.config.StateChangeListener;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import com.apixandru.rummikub.brotocol.util.SocketPacketProcessor;
import com.apixandru.rummikub.client.game.ClientGameConfigurer;
import com.apixandru.rummikub.client.waiting.ClientWaitingRoomConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector {

    private final SocketWrapper socketWrapper;
    private final StateChangeListener stateChangeListener;
    private final ClientPacketHandler packetHandler = new ClientPacketHandler();
    private final SocketPacketProcessor socketPacketProcessor;

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener stateChangeListener, ConnectionListener connectionListener) {
        this.socketWrapper = socketWrapper;
        this.stateChangeListener = stateChangeListener;

        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, this.packetHandler, connectionListener);
    }

    public void connect() {
        packetHandler.setStartGameListener(() -> stateChangeListener.enteredGame(new ClientGameConfigurer(packetHandler, this.socketWrapper)));
        ClientWaitingRoomConfigurer waitingRoomConfigurer = new ClientWaitingRoomConfigurer(packetHandler, socketWrapper);
        stateChangeListener.enteredWaitingRoom(waitingRoomConfigurer);
        new Thread(socketPacketProcessor).start();
    }

}
