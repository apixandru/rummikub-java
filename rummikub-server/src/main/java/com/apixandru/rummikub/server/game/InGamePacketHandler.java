package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.util.AbstractMultiPacketHandler;
import com.apixandru.rummikub.model.Rummikub;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
public final class InGamePacketHandler extends AbstractMultiPacketHandler {

    private final ServerBoardListener boardListener;
    private final ServerGameEventListener gameEventListener;

    private final Player<Integer> player;
    private final Rummikub<Integer> rummikubGame;

    public InGamePacketHandler(String playerName, PacketWriter packetWriter, Rummikub<Integer> rummikubGame) {
        this.rummikubGame = rummikubGame;

        packetWriter.writePacket(new PacketPlayerStart());

        this.boardListener = new ServerBoardListener(playerName, packetWriter);
        this.gameEventListener = new ServerGameEventListener(playerName, packetWriter);

        rummikubGame.addBoardListener(boardListener);
        rummikubGame.addGameEventListener(gameEventListener);

        this.player = rummikubGame.addPlayer(playerName, new ServerPlayerCallback(packetWriter));

        register(PacketPlaceCard.class, new PlaceCardOnBoardHandler(playerName, player));
        register(PacketEndTurn.class, new EndTurnHandler(playerName, player));
        register(PacketMoveCard.class, new MoveCardHandler(playerName, player));
        register(PacketTakeCard.class, new TakeCardHandler(playerName, player));
    }

    @Override
    public void cleanup(boolean force) {
        rummikubGame.removeBoardListener(boardListener);
        rummikubGame.removeGameEventListener(gameEventListener);
        rummikubGame.removePlayer(player);
    }

}
