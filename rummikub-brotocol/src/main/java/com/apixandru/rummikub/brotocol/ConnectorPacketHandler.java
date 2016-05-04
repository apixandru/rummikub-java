package com.apixandru.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since May 04, 2016
 */
public interface ConnectorPacketHandler extends PacketHandler<Packet> {

    boolean isReady();

}
