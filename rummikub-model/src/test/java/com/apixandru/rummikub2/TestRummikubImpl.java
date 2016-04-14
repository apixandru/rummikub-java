package com.apixandru.rummikub2;

import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub2.RummikubException.Reason;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.apixandru.rummikub2.RummikubException.Reason.ONGOING_GAME;
import static org.mockito.Mockito.mock;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 11, 2016
 */
public class TestRummikubImpl {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private StateChangeListener<Integer> listener;

    private Rummikub<Integer> rummikub;

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

    @Test(expected = RummikubException.class)
    public void testNullName() {
        rummikub.register(null, listener);
    }

    @Test(expected = RummikubException.class)
    public void testEmptyName() {
        rummikub.register("", listener);
    }

    @Test(expected = RummikubException.class)
    public void testNoListener() {
        rummikub.register("Dan", null);
    }

    @Test(expected = RummikubException.class)
    public void testRejectSameName() {
        rummikub.register("Dan", listener);
        rummikub.register("Dan", listener);
    }

    @Test
    public void testJoinWhileInGame() {
        expectExceptionToBeThrownWithReason(ONGOING_GAME);

        rummikub.register("Dan", new EagerToStartGameListener<>());
        rummikub.register("The Man", listener);
    }

    private void expectExceptionToBeThrownWithReason(final Reason reason) {
        expectedException.expect(RummikubException.class);
        expectedException.expect(RummikubExceptionReason.reason(reason));
    }

}
