package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.config.GameConfigurer;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
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

    private final SocketPacketProcessor socketPacketProcessor;
    private final ConnectionListener connectionListener;

    private final ServerRummikubRoomListener serverRummikubRoomListener;

    private final ServerBoardListener boardListener;
    private final ServerGameEventListener gameEventListener;
    private final ServerPlayerCallback playerCallback;

    private TrackingGameConfigurer trackingGameConfigurer;
    private RummikubRoomConfigurer configurer;

    ServerStateChangeListener(final String playerName, final SocketWrapper socketWrapper, final ConnectionListener connectionListener) {
        this.playerName = playerName;
        this.socketWrapper = socketWrapper;

        this.connectionListener = connectionListener;
        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, this);

        this.boardListener = new ServerBoardListener(playerName, socketWrapper);
        this.gameEventListener = new ServerGameEventListener(playerName, socketWrapper);
        this.playerCallback = new ServerPlayerCallback(playerName, socketWrapper);

        this.serverRummikubRoomListener = new ServerRummikubRoomListener(socketWrapper);
    }

    @Override
    public void enteredWaitingRoom(final RummikubRoomConfigurer configurer) {
        cleanup();
        this.configurer = configurer;
        configurer.registerListener(playerName, serverRummikubRoomListener);
        socketPacketProcessor.setPacketHandler(new WaitingRoomPacketHandler(configurer));
    }

    @Override
    public void enteredGame(final GameConfigurer rawConfigurer) {
        cleanup();
        socketWrapper.writePacket(new PacketPlayerStart());
        TrackingGameConfigurer configurer = new TrackingGameConfigurer(rawConfigurer);

        trackingGameConfigurer = configurer;
        configurer.addBoardListener(boardListener);
        configurer.addGameEventListener(gameEventListener);
        Player<Integer> player = configurer.newPlayer(playerCallback);
        socketPacketProcessor.setPacketHandler(new InGamePacketHandler(player));
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
            this.trackingGameConfigurer = null;
        }
        RummikubRoomConfigurer configurer = this.configurer;
        if (null != configurer) {
            configurer.unregisterListener(serverRummikubRoomListener);
            this.configurer = null;
        }
    }

}
