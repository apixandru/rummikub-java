package com.apixandru.rummikub.brotocol.game.client;

import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public class PacketMoveCard implements Packet {

    public int fromX;
    public int fromY;
    public int toX;
    public int toY;

}
