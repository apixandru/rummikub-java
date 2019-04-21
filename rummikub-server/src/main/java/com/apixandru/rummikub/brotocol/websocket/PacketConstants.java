package com.apixandru.rummikub.brotocol.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.connect.client.StartGameRequest;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.game.client.LoginRequest;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.game.server.LoginResponse;
import com.apixandru.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

@SuppressWarnings("unchecked")
final class PacketConstants {

    private static final Map<Class<Packet>, String> PACKET_TYPE_BY_CLASS;
    private static final Map<String, Class<Packet>> PACKET_CLASS_BY_TYPE;

    static {
        List<Class<? extends Packet>> packets = Arrays.asList(
                LoginRequest.class,
                LoginResponse.class,
                PacketPlayerJoined.class,
                StartGameRequest.class,
                PacketPlayerStart.class,
                PacketNewTurn.class,
                PacketReceiveCard.class,
                PacketPlaceCard.class,
                PacketCardPlaced.class,
                PacketMoveCard.class,
                PacketCardRemoved.class,
                PacketTakeCard.class,
                PacketEndTurn.class,
                PacketGameOver.class,
                PacketPlayerLeft.class);

        Map typeByClass = new HashMap<>();
        Map classByType = new HashMap<>();
        for (Class<? extends Packet> clasz : packets) {
            String type = clasz.getSimpleName();
            typeByClass.put(clasz, type);
            classByType.put(type, clasz);
        }
        PACKET_CLASS_BY_TYPE = unmodifiableMap(classByType);
        PACKET_TYPE_BY_CLASS = unmodifiableMap(typeByClass);
    }

    private PacketConstants() {
    }

    static String getPacketType(Packet packet) {
        return getPacketType(packet.getClass());
    }

    private static String getPacketType(Class packetClass) {
        if (!PACKET_TYPE_BY_CLASS.containsKey(packetClass)) {
            throw new IllegalArgumentException("Unexpected packet: " + packetClass);
        }
        return PACKET_TYPE_BY_CLASS.get(packetClass);
    }

    static Class<Packet> getPacketClass(String type) {
        if (!PACKET_CLASS_BY_TYPE.containsKey(type)) {
            throw new IllegalArgumentException("Unexpected packet type: " + type);
        }
        return PACKET_CLASS_BY_TYPE.get(type);
    }

}
