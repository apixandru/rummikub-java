package com.apixandru.games.rummikub.brotocol.game.server;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.rummikub.api.Card;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_CARD_PLACED)
public class PacketCardPlaced implements Packet {

    public Card card;
    public int x;
    public int y;

}
