package com.apixandru.rummikub.connection.packet;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.BROADCAST_SERVER_SHUTDOWN;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 18, 2016
 */
@Header(BROADCAST_SERVER_SHUTDOWN)
public class ServerShutdownBroadcast implements Packet {
}
