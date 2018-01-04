package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.PacketWriter;
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

    private final PacketWriter packetWriter;
    private final StateChangeListener stateChangeListener;
    private final SocketPacketProcessor socketPacketProcessor;

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener stateChangeListener, ConnectionListener connectionListener) {
        this.packetWriter = socketWrapper;
        this.stateChangeListener = stateChangeListener;
        this.socketPacketProcessor = new SocketPacketProcessor(socketWrapper, connectionListener);
    }

    public void connect() {
        goToWaitingRoom();
        new Thread(socketPacketProcessor).start();
    }

    private void goToWaitingRoom() {
        ClientWaitingRoomPacketHandler packetHandler = new ClientWaitingRoomPacketHandler(new ClientStartGameListener());
        this.socketPacketProcessor.setPacketHandler(packetHandler);
        ClientWaitingRoomConfigurer waitingRoomConfigurer = new ClientWaitingRoomConfigurer(packetHandler, packetWriter);
        stateChangeListener.enteredWaitingRoom(waitingRoomConfigurer);
    }

    private class ClientStartGameListener implements StartGameListener, GameEventListener {

        @Override
        public void startGame() {
            ClientPacketHandler packetHandler = new ClientPacketHandler();
            socketPacketProcessor.setPacketHandler(packetHandler);
            ClientGameConfigurer configurer = new ClientGameConfigurer(packetHandler, packetWriter);
            configurer.addGameEventListener(this);
            stateChangeListener.enteredGame(configurer);
        }

        @Override
        public void newTurn(String player) {
        }

        @Override
        public void gameOver(String player, GameOverReason reason) {
            goToWaitingRoom();
        }

    }

}
