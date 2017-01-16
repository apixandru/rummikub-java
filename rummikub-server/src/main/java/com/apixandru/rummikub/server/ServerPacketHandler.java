package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.LeaveRequest;
import com.apixandru.rummikub.brotocol.connect.client.StartGameRequest;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.server.game.EndTurnHandler;
import com.apixandru.rummikub.server.game.MoveCardHandler;
import com.apixandru.rummikub.server.game.PlaceCardOnBoardHandler;
import com.apixandru.rummikub.server.game.PlayerLeftHandler;
import com.apixandru.rummikub.server.game.TakeCardHandler;
import com.apixandru.rummikub.server.waiting.StartHandler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
class ServerPacketHandler extends MultiPacketHandler implements ConnectorPacketHandler {

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final Reference<StartGameListener> startGameListenerProvider = new Reference<>();
    private final Reference<Player<Integer>> playerProvider = new Reference<>();

    ServerPacketHandler() {
        register(PacketPlaceCard.class, new PlaceCardOnBoardHandler(playerProvider));
        register(PacketEndTurn.class, new EndTurnHandler(playerProvider));
        register(PacketMoveCard.class, new MoveCardHandler(playerProvider));
        register(PacketTakeCard.class, new TakeCardHandler(playerProvider));

        register(LeaveRequest.class, new PlayerLeftHandler(continueReading));

        register(StartGameRequest.class, new StartHandler(startGameListenerProvider));
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
    public void connectionCloseRequest() {
        throw new UnsupportedOperationException();
    }

}
