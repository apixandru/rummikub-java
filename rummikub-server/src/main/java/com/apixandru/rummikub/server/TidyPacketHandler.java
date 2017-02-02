package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 22, 2017
 */
interface TidyPacketHandler extends PacketHandler<Packet> {

    void cleanup();

}
