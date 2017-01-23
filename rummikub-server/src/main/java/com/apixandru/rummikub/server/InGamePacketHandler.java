package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.server.game.EndTurnHandler;
import com.apixandru.rummikub.server.game.MoveCardHandler;
import com.apixandru.rummikub.server.game.PlaceCardOnBoardHandler;
import com.apixandru.rummikub.server.game.ServerBoardListener;
import com.apixandru.rummikub.server.game.ServerGameEventListener;
import com.apixandru.rummikub.server.game.ServerPlayerCallback;
import com.apixandru.rummikub.server.game.TakeCardHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
public class InGamePacketHandler extends MultiPacketHandler implements TidyPacketHandler {

    private final ServerBoardListener boardListener;
    private final ServerGameEventListener gameEventListener;

    private final Player<Integer> player;
    private final Rummikub<Integer> rummikubGame;

    public InGamePacketHandler(String playerName, SocketWrapper socketWrapper, Rummikub<Integer> rummikubGame) {
        this.rummikubGame = rummikubGame;

        this.boardListener = new ServerBoardListener(playerName, socketWrapper);
        this.gameEventListener = new ServerGameEventListener(playerName, socketWrapper);

        rummikubGame.addBoardListener(boardListener);
        rummikubGame.addGameEventListener(gameEventListener);

        this.player = rummikubGame.addPlayer(playerName, new ServerPlayerCallback(playerName, socketWrapper));

        register(PacketPlaceCard.class, new PlaceCardOnBoardHandler(player));
        register(PacketEndTurn.class, new EndTurnHandler(player));
        register(PacketMoveCard.class, new MoveCardHandler(player));
        register(PacketTakeCard.class, new TakeCardHandler(player));
    }

    @Override
    public void cleanup() {
        rummikubGame.removeBoardListener(boardListener);
        rummikubGame.removeGameEventListener(gameEventListener);
        rummikubGame.removePlayer(player);
    }

}
