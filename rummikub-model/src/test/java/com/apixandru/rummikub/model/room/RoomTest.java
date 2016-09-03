package com.apixandru.rummikub.model.room;

import org.junit.Before;
import org.junit.Test;

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
        assertThat(room.join(SHIA_LABEOUF))
                .isTrue();

        assertThat(listener.getNumberOfEventsSent())
                .isEqualTo(1);

        assertThat(room.join(SHIA_LABEOUF))
                .isFalse();

        assertThat(listener.getNumberOfEventsSent())
                .isEqualTo(1);
    }

}
