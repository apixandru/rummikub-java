package com.apixandru.rummikub.brotocol.game.client;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CLIENT_LOGIN;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2017
 */
@Header(CLIENT_LOGIN)
public class PacketLogin implements Packet {

    public String playerName;

}
