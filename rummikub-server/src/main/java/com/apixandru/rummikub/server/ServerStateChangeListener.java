package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.server.game.InGamePacketHandler;
import com.apixandru.rummikub.server.waiting.Room;
import com.apixandru.rummikub.server.waiting.WaitingRoomPacketHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
class ServerStateChangeListener implements StateChangeListener {

    private final SocketWrapper socketWrapper;
    private final String playerName;

    private final SocketPacketProcessor socketPacketProcessor;

    ServerStateChangeListener(final String playerName, final SocketWrapper socketWrapper, final SocketPacketProcessor socketPacketProcessor) {
        this.playerName = playerName;
        this.socketWrapper = socketWrapper;
        this.socketPacketProcessor = socketPacketProcessor;
    }

    @Override
    public void enteredWaitingRoom(Room room, StartGameListener startGameListener) {
        setPacketHandler(new WaitingRoomPacketHandler(playerName, socketWrapper, room, startGameListener));
    }

    @Override
    public void enteredGame(final Rummikub<Integer> rummikub) {
        setPacketHandler(new InGamePacketHandler(playerName, socketWrapper, rummikub));
    }

    private void setPacketHandler(TidyPacketHandler packetHandler) {
        socketPacketProcessor.setPacketHandler(packetHandler);
    }

}
