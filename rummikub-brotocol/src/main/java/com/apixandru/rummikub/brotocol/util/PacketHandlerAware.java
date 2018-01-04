package com.apixandru.rummikub.brotocol.util;

import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;

public interface PacketHandlerAware {

    void setPacketHandler(ConnectorPacketHandler packetHandler);

}
