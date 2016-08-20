package com.apixandru.rummikub.connection.packet;

import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.RESPONSE_CONNECTION;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
@Header(RESPONSE_CONNECTION)
public class ConnectionResponse implements Packet {

    public boolean accepted;
    public ReasonCode reasonCode;

    public ConnectionResponse() {
    }

    public ConnectionResponse(boolean accepted, ReasonCode reasonCode) {
        this.accepted = accepted;
        this.reasonCode = reasonCode;
    }

}
