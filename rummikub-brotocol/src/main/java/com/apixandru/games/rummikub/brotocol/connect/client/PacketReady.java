package com.apixandru.games.rummikub.brotocol.connect.client;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CONNECT_CLIENT_READY;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 30, 2016
 */
@Header(CONNECT_CLIENT_READY)
public class PacketReady implements Packet {

    String playerName;
    boolean ready;

}
