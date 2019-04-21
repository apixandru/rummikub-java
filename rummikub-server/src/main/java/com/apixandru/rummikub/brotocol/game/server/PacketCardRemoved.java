package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public class PacketCardRemoved implements Packet {

    public Card card;
    public int x;
    public int y;
    public boolean reset;

}
