package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.PlayerCallback;
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
public class ClientPacketHandler extends MultiPacketHandler {

    private final Collection<GameEventListener> gameEventListener = new ArrayList<>();

    ClientPacketHandler() {
        register(PacketNewTurn.class, new NewTurnHandler(gameEventListener));
        register(PacketGameOver.class, new GameOverHandler(gameEventListener));
    }

    public void addGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListener.add(gameEventListener);
    }

    public void setBoardListener(final BoardListener boardListener) {
        register(PacketCardPlaced.class, new CardPlacedHandler(boardListener));
        register(PacketCardRemoved.class, new CardRemovedHandler(boardListener));
    }

    public void setPlayerCallback(final PlayerCallback<Integer> playerCallback) {
        register(PacketReceiveCard.class, new ReceiveCardHandler(playerCallback));
    }

}
