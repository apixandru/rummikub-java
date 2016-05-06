package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.PlayerCallback;
import com.apixandru.rummikub.api.StartGameListener;
import com.apixandru.rummikub.api.WaitingRoomListener;
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
import com.apixandru.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.client.game.CardPlacedHandler;
import com.apixandru.rummikub.client.game.CardRemovedHandler;
import com.apixandru.rummikub.client.game.GameOverHandler;
import com.apixandru.rummikub.client.game.NewTurnHandler;
import com.apixandru.rummikub.client.game.ReceiveCardHandler;
import com.apixandru.rummikub.client.waiting.PlayerJoinedHandler;
import com.apixandru.rummikub.client.waiting.PlayerLeftHandler;
import com.apixandru.rummikub.client.waiting.PlayerStartHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ClientPacketHandler implements ConnectorPacketHandler {

    private static final Logger log = LoggerFactory.getLogger(ClientPacketHandler.class);

    private final Reference<BoardListener> boardListener = new Reference<>();
    private final Reference<WaitingRoomListener> waitingRoomListener = new Reference<>();
    private final Reference<PlayerCallback<Integer>> playerCallback = new Reference<>();
    private final Reference<GameEventListener> gameEventListener = new Reference<>();
    private final Reference<StartGameListener> startGameListener = new Reference<>();

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    public ClientPacketHandler() {

        handlers.put(PacketPlayerJoined.class, new PlayerJoinedHandler(waitingRoomListener));
        handlers.put(PacketPlayerLeft.class, new PlayerLeftHandler(waitingRoomListener));
        handlers.put(PacketPlayerStart.class, new PlayerStartHandler(startGameListener));

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardListener));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardListener));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListener));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListener, continueReading));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler(playerCallback));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(final Packet packet) {
        handlers.get(packet.getClass()).handle(packet);
    }

    public void setGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListener.set(gameEventListener);
    }

    public void setBoardListener(final BoardListener boardListener) {
        this.boardListener.set(boardListener);
    }

    public void setPlayerCallback(final PlayerCallback<Integer> playerCallback) {
        this.playerCallback.set(playerCallback);
    }

    public void setWaitingRoomListener(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListener.set(waitingRoomListener);
    }

    public void setStartGameListener(StartGameListener startGameListener) {
        this.startGameListener.set(startGameListener);
    }

    @Override
    public boolean isReady() {
        return continueReading.get();
    }
}
