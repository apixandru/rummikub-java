package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.api.StartGameListener;
import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.PacketLeave;
import com.apixandru.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.brotocol2.ConnectionListener;
import com.apixandru.rummikub.server.game.EndTurnHandler;
import com.apixandru.rummikub.server.game.MoveCardHandler;
import com.apixandru.rummikub.server.game.PlaceCardOnBoardHandler;
import com.apixandru.rummikub.server.game.PlayerLeftHandler;
import com.apixandru.rummikub.server.game.TakeCardHandler;
import com.apixandru.rummikub.server.waiting.StartHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
class ServerPacketHandler implements ConnectorPacketHandler {

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final Map<Class, PacketHandler> handlers;

    private final Reference<StartGameListener> startGameListenerProvider = new Reference<>();
    private final Reference<Player<Integer>> playerProvider = new Reference<>();

    ServerPacketHandler() {
        final Map<Class, PacketHandler> handlers = new HashMap<>();

        handlers.put(PacketPlaceCard.class, new PlaceCardOnBoardHandler(playerProvider));
        handlers.put(PacketEndTurn.class, new EndTurnHandler(playerProvider));
        handlers.put(PacketMoveCard.class, new MoveCardHandler(playerProvider));
        handlers.put(PacketTakeCard.class, new TakeCardHandler(playerProvider));

        handlers.put(PacketLeave.class, new PlayerLeftHandler(continueReading));

        handlers.put(PacketStart.class, new StartHandler(startGameListenerProvider));

        this.handlers = Collections.unmodifiableMap(handlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(final Packet packet) {
        handlers.get(packet.getClass()).handle(packet);
    }

    void setStartGameListenerProvider(final StartGameListener startGameListener) {
        startGameListenerProvider.set(startGameListener);
    }

    void setPlayer(final Player<Integer> player) {
        playerProvider.set(player);
    }

    void reset() {
        setStartGameListenerProvider(null);
        setPlayer(null);
    }

    @Override
    public boolean isReady() {
        return continueReading.get();
    }

    @Override
    @Deprecated
    public void addConnectionListener(ConnectionListener connectionListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void connectionLost() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void connectionCloseRequest() {
        throw new UnsupportedOperationException();
    }

}
