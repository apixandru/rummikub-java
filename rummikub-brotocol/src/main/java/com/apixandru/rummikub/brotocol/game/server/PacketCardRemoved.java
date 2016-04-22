package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_CARD_REMOVED)
public class PacketCardRemoved implements Packet {

    public Card card;
    public int x;
    public int y;
    public boolean reset;

}
