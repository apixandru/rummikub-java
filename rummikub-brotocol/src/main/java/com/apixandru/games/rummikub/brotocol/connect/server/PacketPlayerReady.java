package com.apixandru.games.rummikub.brotocol.connect.server;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CONNECT_SERVER_PLAYER_READY;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 30, 2016
 */
@Header(CONNECT_SERVER_PLAYER_READY)
public class PacketPlayerReady implements Packet {

    String playerName;
    boolean ready;
    boolean canStart;

}
