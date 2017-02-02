package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.PlayerCallback;
import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.rummikub.client.game.CardPlacedHandler;
import com.apixandru.rummikub.client.game.CardRemovedHandler;
import com.apixandru.rummikub.client.game.GameOverHandler;
import com.apixandru.rummikub.client.game.NewTurnHandler;
import com.apixandru.rummikub.client.game.ReceiveCardHandler;
import com.apixandru.rummikub.client.waiting.PlayerJoinedHandler;
import com.apixandru.rummikub.client.waiting.PlayerLeftHandler;
import com.apixandru.rummikub.client.waiting.PlayerStartHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ClientPacketHandler implements ConnectorPacketHandler {

    private final Reference<BoardListener> boardListener = new Reference<>();
    private final Reference<RummikubRoomListener> waitingRoomListener = new Reference<>();
    private final Reference<PlayerCallback<Integer>> playerCallback = new Reference<>();

    private final Collection<GameEventListener> gameEventListener = new ArrayList<>();

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    ClientPacketHandler(StartGameListener startGameListener) {
        handlers.put(PacketPlayerJoined.class, new PlayerJoinedHandler(waitingRoomListener));
        handlers.put(PacketPlayerLeft.class, new PlayerLeftHandler(waitingRoomListener));
        handlers.put(PacketPlayerStart.class, new PlayerStartHandler(startGameListener));

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardListener));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardListener));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListener));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListener));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler(playerCallback));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(final Packet packet) {
        handlers.get(packet.getClass()).handle(packet);
    }

    public void addGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListener.add(gameEventListener);
    }

    public void setBoardListener(final BoardListener boardListener) {
        this.boardListener.set(boardListener);
    }

    public void setPlayerCallback(final PlayerCallback<Integer> playerCallback) {
        this.playerCallback.set(playerCallback);
    }

    public void setWaitingRoomListener(final RummikubRoomListener waitingRoomListener) {
        this.waitingRoomListener.set(waitingRoomListener);
    }

    @Override
    public boolean isReady() {
        return true;
    }

}
