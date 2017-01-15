package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
public final class MultiPacketHandler implements PacketHandler<Packet> {

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    public final <P extends Packet> void register(final Class<P> packetClass, final PacketHandler<P> packetHandler) {
        handlers.put(packetClass, packetHandler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void handle(final Packet packet) {
        handlers.get(packet.getClass()).handle(packet);
    }

}
