package com.apixandru.rummikub.brotocol.util;

import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.PacketReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public final class SocketPacketProcessor implements Runnable, PacketHandlerAware {

    private static final Logger log = LoggerFactory.getLogger(SocketPacketProcessor.class);

    private final PacketReader reader;
    private final ConnectionListener connectionListener;

    private ConnectorPacketHandler packetHandler;

    public SocketPacketProcessor(final PacketReader reader,
                                 final ConnectionListener connectionListener) {
        this.reader = reader;
        this.connectionListener = connectionListener;
    }

    @Override
    public void setPacketHandler(ConnectorPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    @Override
    public void run() {
        while (packetHandler.isReady()) {
            try {
                packetHandler.handle(reader.readPacket());
            } catch (IOException e) {
                log.error("Failed to handle packet", e);
                break;
            }
        }
        connectionListener.onConnectionLost();
    }

}
