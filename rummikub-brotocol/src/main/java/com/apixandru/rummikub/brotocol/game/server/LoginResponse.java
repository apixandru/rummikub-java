package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2017
 */
public class LoginResponse implements Packet {

    public boolean accepted;
    public String reason;

}
