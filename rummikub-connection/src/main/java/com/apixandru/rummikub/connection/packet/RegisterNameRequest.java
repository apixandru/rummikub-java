package com.apixandru.rummikub.connection.packet;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.REQUEST_REGISTER_NAME;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
@Header(REQUEST_REGISTER_NAME)
public class RegisterNameRequest implements Packet {


}
