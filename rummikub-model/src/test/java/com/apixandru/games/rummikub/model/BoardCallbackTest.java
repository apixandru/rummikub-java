package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.model.support.BoardAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        board = new Board();
        this.board.addBoardListener(callback);
        board.placeCard(TestUtils.BLACK_ONE_1, 0, 6);
    }

    /**
     * Should fire "the same event" twice.
     */
    @Test
    public void testFirePlaceCardSame() {
        board.placeCard(TestUtils.BLACK_ONE_1, 0, 6);

        final List<BoardAction> actions = callback.getActions();
        assertEquals(2, actions.size());

        for (int i = 0; i < 2; i++) {
            final BoardAction boardAction = actions.get(i);

            Assert.assertEquals(BoardAction.Action.ADDED, boardAction.action);
            Assert.assertEquals(TestUtils.BLACK_ONE_1, boardAction.card);
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

        board.placeCard(TestUtils.BLACK_ONE_2, 0, 6);

        TestUtils.assertEmpty(callback.getActions());
    }

    /**
     * Should not fire if the card is not actually placed.
     */
    @Test
    public void testFireOnPlaceCard() {
        final List<BoardAction> actions = callback.getActions();
        assertEquals(1, actions.size());
        final BoardAction boardAction = actions.get(0);

        Assert.assertEquals(BoardAction.Action.ADDED, boardAction.action);
        Assert.assertEquals(TestUtils.BLACK_ONE_1, boardAction.card);
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

        Assert.assertEquals(BoardAction.Action.REMOVED, boardAction.action);
        Assert.assertEquals(TestUtils.BLACK_ONE_1, boardAction.card);
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
        Assert.assertEquals(BoardAction.Action.REMOVED, actionRemove.action);
        Assert.assertEquals(TestUtils.BLACK_ONE_1, actionRemove.card);
        assertEquals(0, actionRemove.x);
        assertEquals(6, actionRemove.y);

        final BoardAction actionAdded = actions.get(1);
        Assert.assertEquals(BoardAction.Action.ADDED, actionAdded.action);
        Assert.assertEquals(TestUtils.BLACK_ONE_1, actionAdded.card);
        assertEquals(1, actionAdded.x);
        assertEquals(5, actionAdded.y);
    }

}
