package com.apixandru.rummikub.model;

import com.apixandru.rummikub.model.RummikubException.Reason;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.apixandru.rummikub.model.RummikubException.Reason.NAME_TAKEN;
import static com.apixandru.rummikub.model.RummikubException.Reason.NO_LISTENER;
import static com.apixandru.rummikub.model.RummikubException.Reason.NO_NAME;
import static com.apixandru.rummikub.model.RummikubException.Reason.ONGOING_GAME;
import static org.mockito.Mockito.mock;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 11, 2016
 */
public class TestRummikubImpl {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private StateChangeListener listener;

    private Rummikub rummikub;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        listener = mock(StateChangeListener.class);
        rummikub = new RummikubImpl();
    }

    @Test
    public void testRegularRegister() {
        rummikub.register("Dan", listener);
    }

    @Test
    public void testNullName() {
        expectExceptionToBeThrownWithReason(NO_NAME);

        rummikub.register(null, listener);
    }

    @Test
    public void testEmptyName() {
        expectExceptionToBeThrownWithReason(NO_NAME);

        rummikub.register("", listener);
    }

    @Test
    public void testNoListener() {
        expectExceptionToBeThrownWithReason(NO_LISTENER);

        rummikub.register("Dan", null);
    }

    @Test
    public void testRejectSameName() {
        expectExceptionToBeThrownWithReason(NAME_TAKEN);

        rummikub.register("Dan", listener);
        rummikub.register("Dan", listener);
    }

    @Test
    public void testJoinWhileInGame() {
        expectExceptionToBeThrownWithReason(ONGOING_GAME);

        rummikub.register("Dan", new EagerToStartGameListener());
        rummikub.register("The Man", listener);
    }

    private void expectExceptionToBeThrownWithReason(final Reason reason) {
        expectedException.expect(RummikubException.class);
        expectedException.expect(RummikubExceptionReason.reason(reason));
    }

}
