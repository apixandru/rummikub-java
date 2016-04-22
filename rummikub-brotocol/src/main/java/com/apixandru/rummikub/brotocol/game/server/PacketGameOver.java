package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.SERVER_GAME_OVER;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_GAME_OVER)
public class PacketGameOver implements Packet {

    public String player;

    public GameOverReason reason;

}
