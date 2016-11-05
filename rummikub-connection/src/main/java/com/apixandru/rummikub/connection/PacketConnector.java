package com.apixandru.rummikub.connection;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
interface PacketConnector {

    PacketConnection acceptConnection();

    void stopAccepting();

}
