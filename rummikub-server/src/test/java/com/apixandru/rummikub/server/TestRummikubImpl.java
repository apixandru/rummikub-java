package com.apixandru.rummikub.server;

import com.apixandru.rummikub.server.RummikubException.Reason;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.apixandru.rummikub.server.RummikubException.Reason.NAME_TAKEN;
import static com.apixandru.rummikub.server.RummikubException.Reason.NO_NAME;
import static com.apixandru.rummikub.server.RummikubException.Reason.ONGOING_GAME;
import static org.mockito.Mockito.mock;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 11, 2016
 */
public class TestRummikubImpl {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private StateChangeListener listener;

    private RummikubImpl rummikub;

    @SuppressWarnings("unchecked")
    @Before
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
        expectExceptionToBeThrownWithReason(NO_NAME);

        rummikub.validateCanJoin(null);
    }

    @Test
    public void testEmptyName() {
        expectExceptionToBeThrownWithReason(NO_NAME);

        rummikub.validateCanJoin("");
    }

    @Test
    public void testRejectSameName() {
        expectExceptionToBeThrownWithReason(NAME_TAKEN);

        rummikub.addPlayer("Dan", listener);
        rummikub.validateCanJoin("Dan");
    }

    @Test
    public void testJoinWhileInGame() {
        expectExceptionToBeThrownWithReason(ONGOING_GAME);

        rummikub.addPlayer("Dan", new EagerToStartGameListener());
        rummikub.validateCanJoin("The Man");
    }

    private void expectExceptionToBeThrownWithReason(final Reason reason) {
        expectedException.expect(RummikubException.class);
        expectedException.expect(RummikubExceptionReason.reason(reason));
    }

}
