package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public class PacketNewTurn implements Packet {

    public String playerName;

    public boolean myTurn;

}
