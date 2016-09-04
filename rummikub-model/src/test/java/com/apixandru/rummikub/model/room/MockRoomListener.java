package com.apixandru.rummikub.model.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.apixandru.rummikub.model.room.MockRoomListener.MockRoomListenerEvent.joined;
import static com.apixandru.rummikub.model.room.MockRoomListener.MockRoomListenerEvent.left;
import static java.util.Arrays.asList;
import static java.util.Objects.hash;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
final class MockRoomListener implements RoomListener {

    private final List<MockRoomListenerEvent> events = new ArrayList<>();

    @Override
    public void playerJoined(String playerName) {
        events.add(joined(playerName));
    }

    @Override
    public void playerLeft(String playerName) {
        events.add(left(playerName));
    }

    public void assertSent(MockRoomListenerEvent... events) {
        assertThat(this.events)
                .isEqualTo(asList(events));
        this.events.clear();
    }

    public void assertNoEventSent() {
        assertThat(this.events)
                .as("Expecting no events")
                .isEmpty();
    }

    static class MockRoomListenerEvent {

        private final boolean joined;
        private final String playerName;

        private MockRoomListenerEvent(boolean joined, String playerName) {
            this.joined = joined;
            this.playerName = playerName;
        }

        static MockRoomListenerEvent joined(String playerName) {
            return new MockRoomListenerEvent(true, playerName);
        }

        static MockRoomListenerEvent left(String playerName) {
            return new MockRoomListenerEvent(false, playerName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MockRoomListenerEvent that = (MockRoomListenerEvent) o;
            return joined == that.joined &&
                    Objects.equals(playerName, that.playerName);
        }

        @Override
        public int hashCode() {
            return hash(joined, playerName);
        }

        @Override
        public String toString() {
            return playerName + " " + (joined ? "Joined" : "Left");
        }

    }

}
