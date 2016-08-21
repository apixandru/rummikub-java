package com.apixandru.rummikub.connection.handler;

import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.connection.packet.client.ExitRequest;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
public final class ExitRequestHandler implements PacketHandler<ExitRequest> {

    private final Runnable runnable;

    public ExitRequestHandler(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void handle(ExitRequest packet) {
        runnable.run();
    }

}
