package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.config.GameConfigurer;
import com.apixandru.rummikub.api.config.RummikubRoomConfigurer;
import com.apixandru.rummikub.api.config.StateChangeListener;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import com.apixandru.rummikub.brotocol.util.SocketPacketProcessor;
import com.apixandru.rummikub.server.game.ServerBoardListener;
import com.apixandru.rummikub.server.game.ServerGameEventListener;
import com.apixandru.rummikub.server.game.ServerPlayerCallback;
import com.apixandru.rummikub.server.game.TrackingGameConfigurer;
import com.apixandru.rummikub.server.waiting.ServerRummikubRoomListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
class ServerStateChangeListener implements StateChangeListener, Runnable, ConnectionListener {

    private final SocketWrapper socketWrapper;
    private final String playerName;
    private final ServerPacketHandler serverPacketHandler;
    private final SocketPacketProcessor socketPacketProcessor;
    private final ConnectionListener connectionListener;

    private ServerRummikubRoomListener serverRummikubRoomListener;
    private TrackingGameConfigurer trackingGameConfigurer;

    ServerStateChangeListener(final String playerName, final SocketWrapper socketWrapper, final ConnectionListener connectionListener) {
        this.playerName = playerName;
        this.socketWrapper = socketWrapper;

        this.serverPacketHandler = new ServerPacketHandler();
        this.connectionListener = connectionListener;
        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, this.serverPacketHandler, this);
    }

    @Override
    public void enteredWaitingRoom(final RummikubRoomConfigurer configurer) {
        cleanup();
        serverPacketHandler.reset();
        serverRummikubRoomListener = new ServerRummikubRoomListener(configurer, socketWrapper);
        configurer.registerListener(serverRummikubRoomListener);
        serverPacketHandler.setStartGameListenerProvider(configurer);
    }

    @Override
    public void enteredGame(final GameConfigurer rawConfigurer) {
        cleanup();
        serverPacketHandler.reset();
        socketWrapper.writePacket(new PacketPlayerStart());
        TrackingGameConfigurer configurer = new TrackingGameConfigurer(rawConfigurer);

        trackingGameConfigurer = configurer;
        configurer.addBoardListener(new ServerBoardListener(playerName, socketWrapper));
        configurer.addGameEventListener(new ServerGameEventListener(playerName, socketWrapper));
        serverPacketHandler.setPlayer(configurer.newPlayer(new ServerPlayerCallback(playerName, socketWrapper)));
    }

    @Override
    public void run() {
        this.socketPacketProcessor.run();
    }

    @Override
    public void onConnectionLost() {
        cleanup();
        connectionListener.onConnectionLost();
    }

    private void cleanup() {
        TrackingGameConfigurer trackingGameConfigurer = this.trackingGameConfigurer;
        if (null != trackingGameConfigurer) {
            trackingGameConfigurer.cleanup();
        }
        ServerRummikubRoomListener serverRummikubRoomListener = this.serverRummikubRoomListener;
        if (null != serverRummikubRoomListener) {
            serverRummikubRoomListener.cleanup();
        }
    }

}
