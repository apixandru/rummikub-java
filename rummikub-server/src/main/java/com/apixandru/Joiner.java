package com.apixandru;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 21, 2016
 */
public interface Joiner {

    void attemptToJoin(SocketWrapper wrapper) throws IOException;

}
