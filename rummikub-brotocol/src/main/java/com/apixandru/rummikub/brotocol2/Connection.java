package com.apixandru.rummikub.brotocol2;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public interface Connection extends Closeable {

    DataOutputStream getDataOutputStream();

    DataInputStream getDataInputStream();

}
