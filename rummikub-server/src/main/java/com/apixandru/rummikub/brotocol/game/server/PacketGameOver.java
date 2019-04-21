package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public class PacketGameOver implements Packet {

    public String player;

    public GameOverReason reason;

}
