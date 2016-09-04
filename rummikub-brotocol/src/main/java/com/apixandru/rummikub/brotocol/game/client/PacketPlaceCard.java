package com.apixandru.rummikub.brotocol.game.client;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CLIENT_PLACE_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(CLIENT_PLACE_CARD)
public class PacketPlaceCard implements Packet {

    public Card card;
    public int x;
    public int y;

}
