package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.SERVER_LOGIN_RESPONSE;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2017
 */
@Header(SERVER_LOGIN_RESPONSE)
public class PacketLoginResponse implements Packet {

    public boolean accepted;
    public String reason;

}
