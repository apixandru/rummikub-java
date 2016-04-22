package com.apixandru.games.rummikub.brotocol.game.client;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.rummikub.api.Card;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_MOVE_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(CLIENT_MOVE_CARD)
public class PacketMoveCard implements Packet {

    public Card card;
    public int fromX;
    public int fromY;
    public int toX;
    public int toY;

}
