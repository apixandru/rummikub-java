package com.apixandru.rummikub.server;

import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.games.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.games.rummikub.server.PlayerProvider;
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

    private final StartGameListener startGameListener;
    private final PlayerProvider<Integer> playerProvider;

    public ServerPacketHandler(final StartGameListener startGameListener, final PlayerProvider<Integer> playerProvider) {
        this.startGameListener = startGameListener;
        this.playerProvider = playerProvider;
        this.handlers = createHandlers();
    }

    private Map<Class, PacketHandler> createHandlers() {
        final Map<Class, PacketHandler> handlers = new HashMap<>();

        handlers.put(PacketPlaceCard.class, new PlaceCardOnBoardHandler(playerProvider));
        handlers.put(PacketEndTurn.class, new EndTurnHandler(playerProvider));
        handlers.put(PacketMoveCard.class, new MoveCardHandler(playerProvider));
        handlers.put(PacketTakeCard.class, new TakeCardHandler(playerProvider));

        handlers.put(PacketStart.class, new StartHandler(startGameListener));

        return Collections.unmodifiableMap(handlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(Packet packet) {
        handlers.get(packet.getClass()).handle(packet);
    }

}
