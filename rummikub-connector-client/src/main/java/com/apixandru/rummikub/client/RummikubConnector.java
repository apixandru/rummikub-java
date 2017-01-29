package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.room.StartGameListener;
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
        this.packetHandler.setStartGameListener(new ClientStartGameListener());
        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, connectionListener);
        this.socketPacketProcessor.setPacketHandler(this.packetHandler);
    }

    public void connect() {
        ClientWaitingRoomConfigurer waitingRoomConfigurer = new ClientWaitingRoomConfigurer(packetHandler, socketWrapper);
        stateChangeListener.enteredWaitingRoom(waitingRoomConfigurer);
        new Thread(socketPacketProcessor).start();
    }

    private class ClientStartGameListener implements StartGameListener {
        @Override
        public void startGame() {
            stateChangeListener.enteredGame(new ClientGameConfigurer(packetHandler, socketWrapper));
        }
    }

}
