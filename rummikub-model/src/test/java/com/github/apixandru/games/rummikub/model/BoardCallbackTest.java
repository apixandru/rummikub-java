package com.github.apixandru.games.rummikub.model;

import com.github.apixandru.games.rummikub.model.support.BoardAction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.apixandru.games.rummikub.model.TestUtils.BLACK_ONE_1;
import static com.github.apixandru.games.rummikub.model.TestUtils.BLACK_ONE_2;
import static com.github.apixandru.games.rummikub.model.TestUtils.assertEmpty;
import static com.github.apixandru.games.rummikub.model.support.BoardAction.Action.ADDED;
import static com.github.apixandru.games.rummikub.model.support.BoardAction.Action.REMOVED;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public final class BoardCallbackTest {

    private final TrackingBoardCallback callback = new TrackingBoardCallback();

    private Board board;

    @Before
    public void setup() {
        callback.clear();
        board = new Board(callback);
        board.placeCard(BLACK_ONE_1, 0, 6);
    }

    /**
     * Should fire "the same event" twice.
     */
    @Test
    public void testFirePlaceCardSame() {
        board.placeCard(BLACK_ONE_1, 0, 6);

        final List<BoardAction> actions = callback.getActions();
        assertEquals(2, actions.size());

        for (int i = 0; i < 2; i++) {
            final BoardAction boardAction = actions.get(i);

            assertEquals(ADDED, boardAction.action);
            assertEquals(BLACK_ONE_1, boardAction.card);
            assertEquals(0, boardAction.x);
            assertEquals(6, boardAction.y);
        }

    }

    /**
     * Should not fire if the card is not actually placed.
     */
    @Test
    public void testShouldntFireIfSlotTaken() {
        callback.clear();

        board.placeCard(BLACK_ONE_2, 0, 6);

        assertEmpty(callback.getActions());
    }

    /**
     * Should not fire if the card is not actually placed.
     */
    @Test
    public void testFireOnPlaceCard() {
        final List<BoardAction> actions = callback.getActions();
        assertEquals(1, actions.size());
        final BoardAction boardAction = actions.get(0);

        assertEquals(ADDED, boardAction.action);
        assertEquals(BLACK_ONE_1, boardAction.card);
        assertEquals(0, boardAction.x);
        assertEquals(6, boardAction.y);
    }


    /**
     * Should fire an event for remove card.
     */
    @Test
    public void testRemoveCardFromBoard() {
        callback.clear();

        board.removeCard(0, 6);

        final List<BoardAction> actions = callback.getActions();
        assertEquals(1, actions.size());
        final BoardAction boardAction = actions.get(0);

        assertEquals(REMOVED, boardAction.action);
        assertEquals(BLACK_ONE_1, boardAction.card);
        assertEquals(0, boardAction.x);
        assertEquals(6, boardAction.y);
    }

    @Test
    public void testMoveCardOnBoard() {
        callback.clear();

        board.moveCard(0, 6, 1, 5);

        final List<BoardAction> actions = callback.getActions();
        assertEquals(2, actions.size());

        final BoardAction actionRemove = actions.get(0);
        assertEquals(REMOVED, actionRemove.action);
        assertEquals(BLACK_ONE_1, actionRemove.card);
        assertEquals(0, actionRemove.x);
        assertEquals(6, actionRemove.y);

        final BoardAction actionAdded = actions.get(1);
        assertEquals(ADDED, actionAdded.action);
        assertEquals(BLACK_ONE_1, actionAdded.card);
        assertEquals(1, actionAdded.x);
        assertEquals(5, actionAdded.y);
    }

}
