package com.apixandru.rummikub.model;

import com.apixandru.rummikub.model.support.BoardAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public final class BoardListenerTest {

    private final TrackingBoardListener listener = new TrackingBoardListener();

    private Board board;

    @BeforeEach
    public void setup() {
        listener.clear();
        board = new BoardImpl();
        this.board.addBoardListener(listener);
        board.placeCard(TestUtils.BLACK_ONE_1, 0, 6);
    }

    /**
     * Should fire "the same event" twice.
     */
    @Test
    public void testFirePlaceCardSame() {
        board.placeCard(TestUtils.BLACK_ONE_1, 0, 6);

        final List<BoardAction> actions = listener.getActions();
        assertEquals(1, actions.size());

        final BoardAction boardAction = actions.get(0);

        assertEquals(BoardAction.Action.ADDED, boardAction.action);
        assertEquals(TestUtils.BLACK_ONE_1, boardAction.card);
        assertEquals(0, boardAction.x);
        assertEquals(6, boardAction.y);

    }

    /**
     * Should not fire if the card is not actually placed.
     */
    @Test
    public void testShouldntFireIfSlotTaken() {
        listener.clear();

        board.placeCard(TestUtils.BLACK_ONE_2, 0, 6);

        TestUtils.assertEmpty(listener.getActions());
    }

    /**
     * Should not fire if the card is not actually placed.
     */
    @Test
    public void testFireOnPlaceCard() {
        final List<BoardAction> actions = listener.getActions();
        assertEquals(1, actions.size());
        final BoardAction boardAction = actions.get(0);

        assertEquals(BoardAction.Action.ADDED, boardAction.action);
        assertEquals(TestUtils.BLACK_ONE_1, boardAction.card);
        assertEquals(0, boardAction.x);
        assertEquals(6, boardAction.y);
    }

    /**
     * Should fire an event for remove card.
     */
    @Test
    public void testRemoveCardFromBoard() {
        listener.clear();

        board.removeCard(0, 6);

        final List<BoardAction> actions = listener.getActions();
        assertEquals(1, actions.size());
        final BoardAction boardAction = actions.get(0);

        assertEquals(BoardAction.Action.REMOVED, boardAction.action);
        assertEquals(TestUtils.BLACK_ONE_1, boardAction.card);
        assertEquals(0, boardAction.x);
        assertEquals(6, boardAction.y);
    }

    @Test
    public void testMoveCardOnBoard() {
        listener.clear();

        board.moveCard(0, 6, 1, 5);

        final List<BoardAction> actions = listener.getActions();
        assertEquals(2, actions.size());

        final BoardAction actionRemove = actions.get(0);
        assertEquals(BoardAction.Action.REMOVED, actionRemove.action);
        assertEquals(TestUtils.BLACK_ONE_1, actionRemove.card);
        assertEquals(0, actionRemove.x);
        assertEquals(6, actionRemove.y);

        final BoardAction actionAdded = actions.get(1);
        assertEquals(BoardAction.Action.ADDED, actionAdded.action);
        assertEquals(TestUtils.BLACK_ONE_1, actionAdded.card);
        assertEquals(1, actionAdded.x);
        assertEquals(5, actionAdded.y);
    }

}
