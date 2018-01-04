package com.apixandru.rummikub.brotocol.util;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;

public interface PacketHandlerAware {

    void setPacketHandler(PacketHandler<Packet> packetHandler);

}
