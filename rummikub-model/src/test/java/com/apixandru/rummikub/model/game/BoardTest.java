package com.apixandru.rummikub.model.game;

import org.junit.Before;
import org.junit.Test;

import static com.apixandru.rummikub.api.Color.BLUE;
import static com.apixandru.rummikub.api.Color.RED;
import static com.apixandru.rummikub.api.Rank.ONE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 01, 2016
 */
public final class BoardTest {

    private Board board;

    @Before
    public void setup() {
        board = new Board();
    }

    /**
     * A group of ones placed one next to another is a valid formation.
     */
    @Test
    public void testValidFormation() {
        board.placeCard(TestUtils.BLACK_ONE_1, 0, 1);
        board.placeCard(TestUtils.card(RED, ONE), 1, 1);
        board.placeCard(TestUtils.card(BLUE, ONE), 2, 1);
        assertTrue(board.isValid());
    }

    /**
     * A group of ones placed one next to another with a gap is not a valid formation.
     */
    @Test
    public void testInvalidFormation() {
        board.placeCard(TestUtils.BLACK_ONE_1, 16, 6);
        board.placeCard(TestUtils.card(RED, ONE), 18, 6);
        board.placeCard(TestUtils.card(BLUE, ONE), 19, 6);
        assertFalse(board.isValid());
        assertEquals(2, board.streamGroups().count());
    }

    /**
     * Should not be able to place cards out of bounds
     */
    @Test
    public void testPlaceOutOfBounds() {
        assertFalse(board.placeCard(TestUtils.BLACK_ONE_1, 0, 7));
        assertFalse(board.placeCard(TestUtils.BLACK_ONE_1, 20, 0));
    }

    /**
     * Should not be able to place different cards on the same place.
     */
    @Test
    public void testPlaceCardOnTakenSpot() {
        assertTrue(board.placeCard(TestUtils.BLACK_ONE_1, 0, 6));
        assertFalse(board.placeCard(TestUtils.BLACK_ONE_2, 0, 6));
    }

    /**
     * Should be able to place the same cards in the same slot.
     */
    @Test
    public void testPlaceSameCardOnTakenSpot() {
        assertTrue(board.placeCard(TestUtils.BLACK_ONE_1, 0, 6));
        assertTrue(board.placeCard(TestUtils.BLACK_ONE_1, 0, 6));
    }

    /**
     * Should be able to remove the card and place another one in its position.
     */
    @Test
    public void testRemoveCardFromBoard() {
        assertTrue(board.placeCard(TestUtils.BLACK_ONE_1, 0, 6));
        assertSame(TestUtils.BLACK_ONE_1, board.removeCard(0, 6));
        assertTrue(board.isFree(0, 6));
    }

    /**
     * Position B should be taken if a card was moved from position A to position B.
     */
    @Test
    public void testMoveCardOnBoard() {
        assertTrue(board.placeCard(TestUtils.BLACK_ONE_1, 0, 6));
        board.moveCard(0, 6, 1, 5);
        assertTrue(board.isFree(0, 6));
        assertFalse(board.isFree(1, 5));
    }

    /**
     * Placing card on the board should make the spot taken.
     */
    @Test
    public void testPlaceCard() {
        assertTrue(board.placeCard(TestUtils.BLACK_ONE_1, 0, 6));
        assertFalse(board.isFree(0, 6));
    }

}
