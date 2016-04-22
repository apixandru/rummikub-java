package com.apixandru.rummikub.brotocol.connect.client;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CONNECT_CLIENT_LEAVE;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 08, 2016
 */
@Header(CONNECT_CLIENT_LEAVE)
public class PacketLeave implements Packet {
}
