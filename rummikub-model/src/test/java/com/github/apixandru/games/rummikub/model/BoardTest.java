package com.github.apixandru.games.rummikub.model;

import org.junit.Before;
import org.junit.Test;

import static com.github.apixandru.games.rummikub.model.Color.BLACK;
import static com.github.apixandru.games.rummikub.model.Color.BLUE;
import static com.github.apixandru.games.rummikub.model.Color.RED;
import static com.github.apixandru.games.rummikub.model.Rank.ONE;
import static com.github.apixandru.games.rummikub.model.TestUtils.card;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 01, 2016
 */
public final class BoardTest {

    private Board board;

    @Before
    public void setup() {
        board = new Board(null);
    }

    /**
     * A group of ones placed one next to another is a valid formation.
     */
    @Test
    public void testValidFormation() {
        board.placeCard(card(BLACK, ONE), 0, 1);
        board.placeCard(card(RED, ONE), 1, 1);
        board.placeCard(card(BLUE, ONE), 2, 1);
        assertTrue(board.isValid());
    }

    /**
     * A group of ones placed one next to another with a gap is not a valid formation.
     */
    @Test
    public void testInvalidFormation() {
        board.placeCard(card(BLACK, ONE), 16, 6);
        board.placeCard(card(RED, ONE), 18, 6);
        board.placeCard(card(BLUE, ONE), 19, 6);
        assertFalse(board.isValid());
        assertEquals(2, board.streamGroups().count());
    }

    /**
     * Should not be able to place cards out of bounds
     */
    @Test
    public void testPlaceOutOfBounds() {
        assertFalse(board.placeCard(card(BLACK, ONE), 0, 7));
        assertFalse(board.placeCard(card(BLACK, ONE), 20, 0));
    }

    /**
     * Should not be able to place different cards on the same place.
     */
    @Test
    public void testPlaceCardOnTakenSpot() {
        assertTrue(board.placeCard(card(BLACK, ONE), 0, 6));
        assertFalse(board.placeCard(card(BLACK, ONE), 0, 6));
    }

    /**
     * Should be able to place the same cards in the same slot.
     */
    @Test
    public void testPlaceSameCardOnTakenSpot() {
        final Card card = card(BLACK, ONE);
        assertTrue(board.placeCard(card, 0, 6));
        assertTrue(board.placeCard(card, 0, 6));
    }

}
