package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.room.StartGameListener;
import com.apixandru.rummikub.brotocol.util.PacketHandlerAware;
import com.apixandru.rummikub.client.game.ClientGameConfigurer;
import com.apixandru.rummikub.client.waiting.ClientWaitingRoomConfigurer;

public final class RummikubWebSocketConnector {

    private final PacketWriter packetWriter;
    private final StateChangeListener stateChangeListener;
    private final PacketHandlerAware packetHandlerAware;

    public RummikubWebSocketConnector(final PacketWriter packetWriter,
                                      final StateChangeListener stateChangeListener,
                                      final PacketHandlerAware packetHandlerAware) {
        this.packetWriter = packetWriter;
        this.stateChangeListener = stateChangeListener;
        this.packetHandlerAware = packetHandlerAware;
    }

    public void connect() {
        goToWaitingRoom();
    }

    private void goToWaitingRoom() {
        ClientWaitingRoomPacketHandler packetHandler = new ClientWaitingRoomPacketHandler(new ClientStartGameListener());
        this.packetHandlerAware.setPacketHandler(packetHandler);
        ClientWaitingRoomConfigurer waitingRoomConfigurer = new ClientWaitingRoomConfigurer(packetHandler, packetWriter);
        stateChangeListener.enteredWaitingRoom(waitingRoomConfigurer);
    }

    private class ClientStartGameListener implements StartGameListener, GameEventListener {

        @Override
        public void startGame() {
            ClientPacketHandler packetHandler = new ClientPacketHandler();
            packetHandlerAware.setPacketHandler(packetHandler);
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
