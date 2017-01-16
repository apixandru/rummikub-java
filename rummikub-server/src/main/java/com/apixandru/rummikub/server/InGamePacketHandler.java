package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.server.game.EndTurnHandler;
import com.apixandru.rummikub.server.game.MoveCardHandler;
import com.apixandru.rummikub.server.game.PlaceCardOnBoardHandler;
import com.apixandru.rummikub.server.game.TakeCardHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
public class InGamePacketHandler extends MultiPacketHandler {

    public InGamePacketHandler(Player<Integer> player) {
        final Reference<Player<Integer>> playerProvider = new Reference<>();
        playerProvider.set(player);
        register(PacketPlaceCard.class, new PlaceCardOnBoardHandler(playerProvider));
        register(PacketEndTurn.class, new EndTurnHandler(playerProvider));
        register(PacketMoveCard.class, new MoveCardHandler(playerProvider));
        register(PacketTakeCard.class, new TakeCardHandler(playerProvider));
    }

}
