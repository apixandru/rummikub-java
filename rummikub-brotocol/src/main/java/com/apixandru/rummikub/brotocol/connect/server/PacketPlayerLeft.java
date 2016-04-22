package com.apixandru.rummikub.brotocol.connect.server;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CONNECT_SERVER_PLAYER_LEFT;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
@Header(CONNECT_SERVER_PLAYER_LEFT)
public class PacketPlayerLeft implements Packet {

    public String playerName;

}
