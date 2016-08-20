package com.apixandru.rummikub.connection.packet.client;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.REQUEST_EXIT;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
@Header(REQUEST_EXIT)
public class ExitRequest implements Packet {
}
