package com.apixandru.rummikub.server;

import com.apixandru.games.rummikub.brotocol.PacketReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
class ServerInputParser implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ServerInputParser.class);

    private final PacketReader reader;

    private final ServerPacketHandler packetHandler;

    ServerInputParser(final PacketReader reader, final ServerPacketHandler packetHandler) {
        this.reader = reader;
        this.packetHandler = packetHandler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                packetHandler.handle(reader.readPacket());
            } catch (IOException e) {
                log.error("Failed to handle packet", e);
                break;
            }
        }
    }

}
