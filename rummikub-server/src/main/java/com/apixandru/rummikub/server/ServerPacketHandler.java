package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.LeaveRequest;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
class ServerPacketHandler implements ConnectorPacketHandler {

    private static final Logger log = getLogger(ServerPacketHandler.class);

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private PacketHandler<Packet> currentStatePacketHandler;

    public void setCurrentStatePacketHandler(PacketHandler<Packet> currentStatePacketHandler) {
        this.currentStatePacketHandler = currentStatePacketHandler;
    }

    @Override
    public boolean isReady() {
        return continueReading.get();
    }

    @Override
    public void handle(Packet packet) {
        if (packet instanceof LeaveRequest) {
            log.debug("Player left");
            continueReading.set(false);
            return;
        }
        currentStatePacketHandler.handle(packet);
    }

    @Override
    @Deprecated
    public void connectionCloseRequest() {
        throw new UnsupportedOperationException();
    }

}
