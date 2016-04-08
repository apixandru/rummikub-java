package com.apixandru.games.rummikub.brotocol.connect.client;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CONNECT_CLIENT_LEAVE;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 08, 2016
 */
@Header(CONNECT_CLIENT_LEAVE)
public class PacketLeave implements Packet {
}
