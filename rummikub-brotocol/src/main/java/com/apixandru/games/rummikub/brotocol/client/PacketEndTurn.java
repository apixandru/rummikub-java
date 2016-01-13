package com.apixandru.games.rummikub.brotocol.client;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_END_TURN;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(CLIENT_END_TURN)
public class PacketEndTurn implements Packet {
}
