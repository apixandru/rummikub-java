package com.apixandru.rummikub.brotocol.game.client;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public class PacketTakeCard implements Packet {

    public Card card;
    public int x;
    public int y;
    public int hint;

}
