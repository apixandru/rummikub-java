package com.apixandru.games.rummikub.brotocol.game.server;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_NEW_TURN;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_NEW_TURN)
public class PacketNewTurn implements Packet {

    public String playerName;

}
