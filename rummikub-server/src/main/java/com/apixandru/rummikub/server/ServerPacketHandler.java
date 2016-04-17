package com.apixandru.rummikub.server;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.games.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.games.rummikub.server.game.EndTurnHandler;
import com.apixandru.games.rummikub.server.game.MoveCardHandler;
import com.apixandru.games.rummikub.server.game.PlaceCardOnBoardHandler;
import com.apixandru.games.rummikub.server.game.TakeCardHandler;
import com.apixandru.games.rummikub.server.waiting.StartHandler;
import com.apixandru.rummikub.waiting.StartGameListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ServerPacketHandler implements PacketHandler<Packet> {

    private final Map<Class, PacketHandler> handlers;

    private final Reference<StartGameListener> startGameListenerProvider = new Reference<>();
    private final Reference<Player<Integer>> playerProvider = new Reference<>();

    public ServerPacketHandler() {
        final Map<Class, PacketHandler> handlers = new HashMap<>();

        handlers.put(PacketPlaceCard.class, new PlaceCardOnBoardHandler(playerProvider));
        handlers.put(PacketEndTurn.class, new EndTurnHandler(playerProvider));
        handlers.put(PacketMoveCard.class, new MoveCardHandler(playerProvider));
        handlers.put(PacketTakeCard.class, new TakeCardHandler(playerProvider));

        handlers.put(PacketStart.class, new StartHandler(startGameListenerProvider));

        this.handlers = Collections.unmodifiableMap(handlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(final Packet packet) {
        handlers.get(packet.getClass()).handle(packet);
    }

    public void setStartGameListenerProvider(final StartGameListener startGameListener) {
        startGameListenerProvider.set(startGameListener);
    }

    public void setPlayer(final Player<Integer> player) {
        playerProvider.set(player);
    }

}
