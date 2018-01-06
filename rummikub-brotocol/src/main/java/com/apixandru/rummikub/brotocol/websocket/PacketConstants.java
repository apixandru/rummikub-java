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
import java.util.List;

final class PacketConstants {

    private static final List<Class<? extends Packet>> PACKET_CLASSES = Arrays.asList(
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
            PacketPlayerLeft.class
    );

    private PacketConstants() {
    }

    static int getPacketCode(Packet packet) {
        return getPacketCode(packet.getClass());
    }

    private static int getPacketCode(Class<? extends Packet> packetClass) {
        int code = PACKET_CLASSES.indexOf(packetClass);
        if (code == -1) {
            throw new IllegalArgumentException("Unexpected packet: " + packetClass);
        }
        return code;
    }

    @SuppressWarnings("unchecked")
    static Class<Packet> getPacketClass(int code) {
        if (code == -1 || code >= PACKET_CLASSES.size()) {
            throw new IllegalArgumentException("Unexpected packet code: " + code);
        }
        return (Class<Packet>) PACKET_CLASSES.get(code);
    }

}
