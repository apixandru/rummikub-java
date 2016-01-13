package com.apixandru.games.rummikub.brotocol.server;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_CARD_REMOVED)
public class PacketCardRemoved implements Packet {
}
