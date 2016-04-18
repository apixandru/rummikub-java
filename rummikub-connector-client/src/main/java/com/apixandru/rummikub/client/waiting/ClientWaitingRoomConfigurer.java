package com.apixandru.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.games.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ClientWaitingRoomConfigurer implements WaitingRoomConfigurer {

    private final PacketWriter packetWriter;
    private final Reference<WaitingRoomListener> listenerReference;

    public ClientWaitingRoomConfigurer(final PacketWriter packetWriter, final Reference<WaitingRoomListener> listenerReference) {
        this.packetWriter = packetWriter;
        this.listenerReference = listenerReference;
    }

    @Override
    public void registerListener(final WaitingRoomListener listener) {
        listenerReference.set(listener);
    }

    @Override
    public void startGame() {
        packetWriter.writePacket(new PacketStart());
    }

}
