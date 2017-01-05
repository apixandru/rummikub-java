package com.apixandru.rummikub.connection.packet;

import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
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
