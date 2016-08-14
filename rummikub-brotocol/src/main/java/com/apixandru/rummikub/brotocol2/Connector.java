package com.apixandru.rummikub.brotocol2;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public interface Connector {

    Connection acceptConnection() throws IOException;

}
