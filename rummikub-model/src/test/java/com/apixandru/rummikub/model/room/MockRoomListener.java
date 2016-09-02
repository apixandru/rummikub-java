package com.apixandru.rummikub.model.room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
public final class MockRoomListener implements RoomListener {

    private final List<MockRoomListenerEvent> events = new ArrayList<>();

    private final Set<String> allPlayersNow = new LinkedHashSet<>();

    @Override
    public void playerJoined(String playerName) {

        assertThat(allPlayersNow.add(playerName))
                .as("Expecting " + playerName + " to not already be in the room")
                .isTrue();

        events.add(new MockRoomListenerEvent(true, playerName));
    }

    @Override
    public void playerLeft(String playerName) {
        assertThat(allPlayersNow.remove(playerName))
                .as("Expecting " + playerName + " to already be in the room")
                .isTrue();

        events.add(new MockRoomListenerEvent(false, playerName));
    }

    public List<MockRoomListenerEvent> getEvents() {
        return events;
    }

    public Collection<String> getAllPlayersNow() {
        return allPlayersNow;
    }

    static class MockRoomListenerEvent {

        final boolean joined;
        final String playerName;

        MockRoomListenerEvent(boolean joined, String playerName) {
            this.joined = joined;
            this.playerName = playerName;
        }

    }

}
