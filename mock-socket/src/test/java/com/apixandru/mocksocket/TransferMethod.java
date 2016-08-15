package com.apixandru.mocksocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 15, 2016
 */
@FunctionalInterface
interface TransferMethod {

    void transfer(DataOutputStream thatOutputStream, DataInputStream otherInputStream) throws IOException;

}
