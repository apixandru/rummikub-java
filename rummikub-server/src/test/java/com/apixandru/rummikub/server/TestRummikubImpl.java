package com.apixandru.rummikub.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.apixandru.rummikub.server.RummikubException.Reason.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 11, 2016
 */
public class TestRummikubImpl {

    private StateChangeListener listener;

    private RummikubImpl rummikub;

    @BeforeEach
    public void setup() {
        listener = mock(StateChangeListener.class);
        rummikub = new RummikubImpl();
    }

    @Test
    public void testRegularRegister() {
        rummikub.validateCanJoin("Dan");
        rummikub.addPlayer("Dan", listener);
    }

    @Test
    public void testNullName() {
        RummikubException ex = assertThrows(RummikubException.class, () -> {
            rummikub.validateCanJoin(null);
        });

        assertSame(NO_NAME, ex.getReason());

    }

    @Test
    public void testEmptyName() {
        RummikubException ex = assertThrows(RummikubException.class, () -> {
            rummikub.validateCanJoin("");
        });

        assertSame(NO_NAME, ex.getReason());
    }

    @Test
    public void testRejectSameName() {
        rummikub.addPlayer("Dan", listener);

        RummikubException ex = assertThrows(RummikubException.class, () -> {
            rummikub.validateCanJoin("Dan");
        });

        assertSame(NAME_TAKEN, ex.getReason());
    }

    @Test
    public void testJoinWhileInGame() {
        EagerToStartGameListener listener = new EagerToStartGameListener();
        rummikub.addPlayer("Dan", listener);
        listener.startGame();

        RummikubException ex = assertThrows(RummikubException.class, () -> {
            rummikub.validateCanJoin("The Man");
        });

        assertSame(ONGOING_GAME, ex.getReason());
    }

}
