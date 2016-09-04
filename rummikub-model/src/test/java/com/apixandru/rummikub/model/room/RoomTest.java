package com.apixandru.rummikub.model.room;

import org.junit.Before;
import org.junit.Test;

import static com.apixandru.rummikub.model.room.MockRoomListener.MockRoomListenerEvent.joined;
import static com.apixandru.rummikub.model.room.MockRoomListener.MockRoomListenerEvent.left;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
public class RoomTest {

    private static final String SHIA_LABEOUF = "Shia LaBeouf";

    private Room room;
    private MockRoomListener listener;

    @Before
    public void setup() {
        room = new Room();
        listener = new MockRoomListener();
        room.addRoomListener(listener);
    }

    @Test
    public void assertJoinTwice() {
        room.join(SHIA_LABEOUF);
        listener.assertSent(joined(SHIA_LABEOUF));

        assertThat(room.join(SHIA_LABEOUF))
                .isFalse();

        listener.assertNoEventSent();
    }

    @Test
    public void assertJoinEventSent() {
        assertThat(room.join(SHIA_LABEOUF))
                .isTrue();

        listener.assertSent(joined(SHIA_LABEOUF));
    }

    @Test
    public void testRemoveListener() {
        room.removeRoomListener(listener);
        room.join(SHIA_LABEOUF);
        listener.assertNoEventSent();
    }

    @Test
    public void testPlayerLeaveNotJoin() {
        assertThat(room.leave(SHIA_LABEOUF))
                .isFalse();
        listener.assertNoEventSent();
    }

    @Test
    public void testPlayerLeave() {
        room.join(SHIA_LABEOUF);

        assertThat(room.leave(SHIA_LABEOUF))
                .isTrue();

        listener.assertSent(joined(SHIA_LABEOUF), left(SHIA_LABEOUF));
    }

}
