package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.room.StartGameListener;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.brotocol.util.PacketHandlerAware;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.server.game.InGamePacketHandler;
import com.apixandru.rummikub.server.waiting.Room;
import com.apixandru.rummikub.server.waiting.WaitingRoomPacketHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
public final class ServerStateChangeListener implements StateChangeListener {

    private final PacketWriter packetWriter;
    private final String playerName;

    private final PacketHandlerAware packetHandlerAware;

    public ServerStateChangeListener(final String playerName,
                                     final PacketWriter packetWriter,
                                     final PacketHandlerAware packetHandlerAware) {
        this.playerName = playerName;
        this.packetWriter = packetWriter;
        this.packetHandlerAware = packetHandlerAware;
    }

    @Override
    public void enteredWaitingRoom(Room room, StartGameListener startGameListener) {
        setPacketHandler(new WaitingRoomPacketHandler(playerName, packetWriter, room, startGameListener));
    }

    @Override
    public void enteredGame(final Rummikub<Integer> rummikub) {
        setPacketHandler(new InGamePacketHandler(playerName, packetWriter, rummikub));
    }

    private void setPacketHandler(MultiPacketHandler packetHandler) {
        packetHandlerAware.setPacketHandler(packetHandler);
    }

}
