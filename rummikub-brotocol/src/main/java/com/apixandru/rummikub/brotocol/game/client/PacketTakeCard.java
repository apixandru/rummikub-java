package com.apixandru.rummikub.brotocol.game.client;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CLIENT_TAKE_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(CLIENT_TAKE_CARD)
public class PacketTakeCard implements Packet {

    public Card card;
    public int x;
    public int y;
    public int hint;

}
