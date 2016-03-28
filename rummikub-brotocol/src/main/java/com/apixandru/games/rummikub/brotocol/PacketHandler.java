package com.apixandru.games.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public interface PacketHandler<P extends Packet> {

    void handle(P packet);

}
