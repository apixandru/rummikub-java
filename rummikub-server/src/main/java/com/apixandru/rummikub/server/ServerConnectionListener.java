package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 07, 2017
 */
public interface ServerConnectionListener {

    void connectionDropped(SocketWrapper socketWrapper);

}
