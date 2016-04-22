package com.apixandru.rummikub.brotocol.connect.client;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.CONNECT_CLIENT_START;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 30, 2016
 */
@Header(CONNECT_CLIENT_START)
public class PacketStart implements Packet {
}
