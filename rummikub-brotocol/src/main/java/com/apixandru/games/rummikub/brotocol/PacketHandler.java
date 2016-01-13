package com.apixandru.games.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
interface PacketHandler<P extends Packet> {

    /**
     * @param packet the packet to handle
     */
    <H> void handle(P packet);

}
