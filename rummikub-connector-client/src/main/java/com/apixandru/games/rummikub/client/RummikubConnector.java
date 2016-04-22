package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.brotocol.util.SocketPacketProcessor;
import com.apixandru.rummikub.client.ClientPacketHandler;
import com.apixandru.rummikub.client.game.ClientGameConfigurer;
import com.apixandru.rummikub.client.waiting.ClientWaitingRoomConfigurer;
import com.apixandru.rummikub.model.StateChangeListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector {

    final SocketWrapper socketWrapper;
    final StateChangeListener stateChangeListener;
    private final ClientPacketHandler packetHandler = new ClientPacketHandler();
    private final SocketPacketProcessor socketPacketProcessor;

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener stateChangeListener) {
        this.socketWrapper = socketWrapper;
        this.stateChangeListener = stateChangeListener;

        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, this.packetHandler);
    }

    public void connect() {
        newConnect();
    }

    private void newConnect() {
        packetHandler.setStartGameListener(() -> stateChangeListener.enteredGame(new ClientGameConfigurer(packetHandler, this.socketWrapper)));
        ClientWaitingRoomConfigurer waitingRoomConfigurer = new ClientWaitingRoomConfigurer(packetHandler, socketWrapper);
        stateChangeListener.enteredWaitingRoom(waitingRoomConfigurer);
        new Thread(socketPacketProcessor).start();
    }

}
