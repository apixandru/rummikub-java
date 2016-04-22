package com.apixandru.rummikub.brotocol.game.client;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CLIENT_END_TURN;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(CLIENT_END_TURN)
public class PacketEndTurn implements Packet {
}
