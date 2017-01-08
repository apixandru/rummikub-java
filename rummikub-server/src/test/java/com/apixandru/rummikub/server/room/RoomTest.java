package com.apixandru.rummikub.server.room;

import org.junit.Before;
import org.junit.Test;

import static com.apixandru.rummikub.server.room.MockRummikubRoomListener.MockRoomListenerEvent.joined;
import static com.apixandru.rummikub.server.room.MockRummikubRoomListener.MockRoomListenerEvent.left;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 23, 2016
 */
public class RoomTest {

    private static final String SHIA_LABEOUF = "Shia LaBeouf";
    private static final String CHRISTIAN_BALE = "Christian Bale";

    private Room room;
    private MockRummikubRoomListener listener;

    @Before
    public void setup() {
        room = new Room();
        listener = new MockRummikubRoomListener();
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

    @Test
    public void testNotifyAllJoined() {
        room.removeRoomListener(listener);

        room.join(SHIA_LABEOUF);
        room.join(CHRISTIAN_BALE);

        room.addRoomListener(listener);

        listener.assertSent(joined(SHIA_LABEOUF), joined(CHRISTIAN_BALE));
    }

    @Test
    public void testNotifyAllJoinedButNotLeft() {
        room.removeRoomListener(listener);

        room.join(SHIA_LABEOUF);
        room.join(CHRISTIAN_BALE);

        room.leave(SHIA_LABEOUF);

        room.addRoomListener(listener);

        listener.assertSent(joined(CHRISTIAN_BALE));
    }

}