package com.apixandru.rummikub.connection;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public interface PacketConnector {

    PacketConnection acceptConnection() throws IOException;

    int getPort();

    void stopAccepting();

}
