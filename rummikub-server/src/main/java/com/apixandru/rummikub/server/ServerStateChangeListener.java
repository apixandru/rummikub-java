package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.GameConfigurer;
import com.apixandru.rummikub.api.StateChangeListener;
import com.apixandru.rummikub.api.WaitingRoomConfigurer;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import com.apixandru.rummikub.brotocol.util.SocketPacketProcessor;
import com.apixandru.rummikub.server.game.ServerBoardListener;
import com.apixandru.rummikub.server.game.ServerGameEventListener;
import com.apixandru.rummikub.server.game.ServerPlayerCallback;
import com.apixandru.rummikub.server.game.TrackingGameConfigurer;
import com.apixandru.rummikub.server.waiting.ServerWaitingRoomListener;

import java.util.Optional;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
class ServerStateChangeListener implements StateChangeListener, Runnable, ConnectionListener {

    private final SocketWrapper socketWrapper;
    private final String playerName;
    private final ServerPacketHandler serverPacketHandler;
    private final SocketPacketProcessor socketPacketProcessor;

    private Optional<TrackingGameConfigurer> trackingGameConfigurer = Optional.empty();

    ServerStateChangeListener(final String playerName, final SocketWrapper socketWrapper, final ConnectionListener connectionListener) {
        this.playerName = playerName;
        this.socketWrapper = socketWrapper;

        this.serverPacketHandler = new ServerPacketHandler();
        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, this.serverPacketHandler, this);
    }

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurer configurer) {

//        trackingGameConfigurer.ifPresent(TrackingGameConfigurer::cleanup);
        serverPacketHandler.reset();
        configurer.registerListener(new ServerWaitingRoomListener(socketWrapper));
        serverPacketHandler.setStartGameListenerProvider(configurer);
    }

    @Override
    public void enteredGame(final GameConfigurer rawConfigurer) {
        serverPacketHandler.reset();
        socketWrapper.writePacket(new PacketPlayerStart());
        TrackingGameConfigurer configurer = new TrackingGameConfigurer(rawConfigurer);

        trackingGameConfigurer = Optional.of(configurer);
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
        trackingGameConfigurer.ifPresent(TrackingGameConfigurer::cleanup);
    }

}
