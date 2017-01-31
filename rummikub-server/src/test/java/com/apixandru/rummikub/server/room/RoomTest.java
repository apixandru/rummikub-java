package com.apixandru.rummikub.server.room;

import org.junit.Before;
import org.junit.Test;

import static com.apixandru.rummikub.server.room.MockRummikubRoomListener.MockRoomListenerEvent.joined;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 23, 2016
 */
public class RoomTest {

    private static final String SHIA_LABEOUF = "Shia LaBeouf";
    private static final String CHRISTIAN_BALE = "Christian Bale";

    private Room room;
    private MockRummikubRoomListener shiaListener;
    private MockRummikubRoomListener christianListener;

    @Before
    public void setup() {
        room = new Room();
        shiaListener = new MockRummikubRoomListener();
        christianListener = new MockRummikubRoomListener();
    }

    @Test
    public void assertJoinEventSent() {
        room.join(SHIA_LABEOUF, shiaListener);
        room.join(CHRISTIAN_BALE, christianListener);

        christianListener.assertSent(joined(SHIA_LABEOUF), joined(CHRISTIAN_BALE));
        shiaListener.assertSent(joined(SHIA_LABEOUF), joined(CHRISTIAN_BALE));
    }

    @Test
    public void testRemoveListener() {
        room.join(CHRISTIAN_BALE, christianListener);
        room.leave(CHRISTIAN_BALE);
        christianListener.clearReceived();

        room.join(SHIA_LABEOUF, shiaListener);
        christianListener.assertNoEventSent();
    }

    @Test
    public void testPlayerLeaveNotJoin() {
        room.join(CHRISTIAN_BALE, christianListener);
        christianListener.clearReceived();

        room.leave(SHIA_LABEOUF);
        christianListener.assertNoEventSent();
    }

    @Test
    public void testPlayerLeave() {
        room.join(SHIA_LABEOUF, shiaListener);
        room.leave(SHIA_LABEOUF);

        shiaListener.assertSent(joined(SHIA_LABEOUF));
    }

    @Test
    public void testNotifyAllJoinedButNotLeft() {
        room.join(SHIA_LABEOUF, shiaListener);
        room.leave(SHIA_LABEOUF);

        room.join(CHRISTIAN_BALE, christianListener);

        christianListener.assertSent(joined(CHRISTIAN_BALE));
    }

}
