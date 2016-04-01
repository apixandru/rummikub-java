package com.apixandru.games.rummikub.brotocol.connect.server;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CONNECT_SERVER_PLAYER_LEFT;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
@Header(CONNECT_SERVER_PLAYER_LEFT)
public class PacketPlayerLeft implements Packet {

    public String playerName;

}
