package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.PlayerCallback;
import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.client.game.CardPlacedHandler;
import com.apixandru.rummikub.client.game.CardRemovedHandler;
import com.apixandru.rummikub.client.game.GameOverHandler;
import com.apixandru.rummikub.client.game.NewTurnHandler;
import com.apixandru.rummikub.client.game.ReceiveCardHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ClientPacketHandler extends MultiPacketHandler implements ConnectorPacketHandler {

    private final Reference<BoardListener> boardListener = new Reference<>();
    private final Reference<PlayerCallback<Integer>> playerCallback = new Reference<>();

    private final Collection<GameEventListener> gameEventListener = new ArrayList<>();

    ClientPacketHandler() {
        register(PacketCardPlaced.class, new CardPlacedHandler(boardListener));
        register(PacketCardRemoved.class, new CardRemovedHandler(boardListener));
        register(PacketNewTurn.class, new NewTurnHandler(gameEventListener));
        register(PacketGameOver.class, new GameOverHandler(gameEventListener));
        register(PacketReceiveCard.class, new ReceiveCardHandler(playerCallback));
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

    @Override
    public boolean isReady() {
        return true;
    }

}
