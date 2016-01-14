package com.apixandru.games.rummikub.brotocol.client;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_PLACE_CARD;

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
