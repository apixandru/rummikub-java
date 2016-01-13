package com.apixandru.games.rummikub.brotocol.server;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_GAME_OVER;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_GAME_OVER)
public class PacketGameOver implements Packet {
}
