package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol2.ConnectionListener;
import com.apixandru.rummikub.connection.handler.ExitRequestHandler;
import com.apixandru.rummikub.connection.packet.client.ExitRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
class ServerConnectorPacketHandler implements ConnectorPacketHandler, ConnectionListener {

    private final AtomicBoolean continueListening = new AtomicBoolean(true);

    private final List<ConnectionListener> connectionListeners = new ArrayList<>();

    private final Map<Class, PacketHandler> packets = new LinkedHashMap<>();

    ServerConnectorPacketHandler() {
        register(ExitRequest.class, new ExitRequestHandler(this::connectionCloseRequest));
    }

    private <P extends Packet> void register(final Class<P> packetClass, final PacketHandler<P> packetHandler) {
        this.packets.put(packetClass, packetHandler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handle(Packet packet) {
        if (!isReady()) {
            return;
        }
        Class<? extends Packet> packetClass = packet.getClass();
        PacketHandler<Packet> packetHandler = packets.get(packetClass);
        if (null == packetHandler) {
            throw new IllegalArgumentException("Unexpected packet " + packetClass);
        }
        packetHandler.handle(packet);
    }

    @Override
    public boolean isReady() {
        return continueListening.get();
    }

    void addConnectionListener(ConnectionListener connectionListener) {
        connectionListeners.add(connectionListener);
    }

    @Override
    public void connectionCloseRequest() {
        continueListening.set(false);
        connectionListeners.forEach(ConnectionListener::connectionCloseRequest);
    }

}
