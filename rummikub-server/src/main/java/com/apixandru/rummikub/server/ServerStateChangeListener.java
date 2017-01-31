package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.model.Rummikub;

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
    public void enteredWaitingRoom(final RummikubRoomConfigurer configurer) {
        socketPacketProcessor.setPacketHandler(new WaitingRoomPacketHandler(playerName, socketWrapper, configurer));
    }

    @Override
    public void enteredGame(final Rummikub<Integer> rummikub) {
        socketPacketProcessor.setPacketHandler(new InGamePacketHandler(playerName, socketWrapper, rummikub));
    }

}
