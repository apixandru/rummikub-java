package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.PacketReader;
import com.apixandru.rummikub.brotocol.connect.client.LeaveRequest;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
final class SocketPacketProcessor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SocketPacketProcessor.class);

    private final PacketReader reader;
    private final ConnectionListener connectionListener;
    private PacketHandler<Packet> packetHandler;

    SocketPacketProcessor(final PacketReader reader, final ConnectionListener connectionListener) {
        this.reader = reader;
        this.connectionListener = connectionListener;
    }

    void setPacketHandler(PacketHandler<Packet> packetHandler) {
        if (this.packetHandler instanceof TidyPacketHandler) {
            ((TidyPacketHandler) this.packetHandler).cleanup();
        }
        this.packetHandler = packetHandler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Packet packet = reader.readPacket();
                if (packet instanceof LeaveRequest) {
                    log.debug("Player left");
                    break;
                }
                packetHandler.handle(packet);
            } catch (IOException e) {
                log.error("Failed to handle packet", e);
                break;
            }
        }
        connectionListener.onConnectionLost();
    }

}
