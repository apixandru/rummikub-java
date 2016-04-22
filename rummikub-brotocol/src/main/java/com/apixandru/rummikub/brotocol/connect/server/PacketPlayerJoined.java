package com.apixandru.rummikub.brotocol.connect.server;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CONNECT_SERVER_PLAYER_JOINED;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 30, 2016
 */
@Header(CONNECT_SERVER_PLAYER_JOINED)
public class PacketPlayerJoined implements Packet {

    public String playerName;

}
