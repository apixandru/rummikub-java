package com.apixandru.rummikub.client;

import com.apixandru.games.rummikub.api.BoardListener;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.games.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.games.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.games.rummikub.brotocol.util.Reference;
import com.apixandru.games.rummikub.client.ConnectionListener;
import com.apixandru.games.rummikub.client.game.CardPlacedHandler;
import com.apixandru.games.rummikub.client.game.CardRemovedHandler;
import com.apixandru.games.rummikub.client.game.GameOverHandler;
import com.apixandru.games.rummikub.client.game.NewTurnHandler;
import com.apixandru.games.rummikub.client.game.ReceiveCardHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerJoinedHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerLeftHandler;
import com.apixandru.rummikub.waiting.WaitingRoomListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ClientPacketHandler<H> implements PacketHandler<Packet> {

    private static final Logger log = LoggerFactory.getLogger(ClientPacketHandler.class);

    private final Reference<BoardListener> boardListener = new Reference<>();
    private final Reference<WaitingRoomListener> waitingRoomListener = new Reference<>();
    private final Reference<ConnectionListener> connectionListener = new Reference<>();
    private final Reference<PlayerCallback<H>> playerCallback = new Reference<>();
    private final Reference<GameEventListener> gameEventListener = new Reference<>();
    private final List<H> hints = new ArrayList<>();

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    public ClientPacketHandler() {

        handlers.put(PacketPlayerJoined.class, new PlayerJoinedHandler(waitingRoomListener));
        handlers.put(PacketPlayerLeft.class, new PlayerLeftHandler(waitingRoomListener));

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardListener));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardListener));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListener));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListener, continueReading));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler<>(playerCallback, hints));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(final Packet packet) {
        handlers.get(packet.getClass()).handle(packet);
    }

}
