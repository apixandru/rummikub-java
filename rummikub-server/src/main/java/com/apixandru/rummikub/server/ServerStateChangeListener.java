package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import com.apixandru.rummikub.model.Rummikub;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
class ServerStateChangeListener implements StateChangeListener, Runnable {

    private final SocketWrapper socketWrapper;
    private final String playerName;

    private final SocketPacketProcessor socketPacketProcessor;

    ServerStateChangeListener(final String playerName, final SocketWrapper socketWrapper, final ConnectionListener connectionListener) {
        this.playerName = playerName;
        this.socketWrapper = socketWrapper;

        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, connectionListener);
    }

    @Override
    public void enteredWaitingRoom(final RummikubRoomConfigurer configurer) {
        socketPacketProcessor.setPacketHandler(new WaitingRoomPacketHandler(playerName, socketWrapper, configurer));
    }

    @Override
    public void enteredGame(final Rummikub<Integer> rummikub) {
        socketWrapper.writePacket(new PacketPlayerStart());
        socketPacketProcessor.setPacketHandler(new InGamePacketHandler(playerName, socketWrapper, rummikub));
    }

    @Override
    public void run() {
        this.socketPacketProcessor.run();
    }

}
