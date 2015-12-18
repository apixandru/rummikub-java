package com.github.apixandru.games.rummikub.model;

import org.junit.Test;

import static com.github.apixandru.games.rummikub.model.TestUtils.card;
import static com.github.apixandru.games.rummikub.model.TestUtils.joker;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since November 24, 2015
 */
public final class BoardTest {

    @Test
    public void testValidFormation() {
        final Board board = new Board();
        board.placeCard(card(Color.BLACK, Rank.ONE), 0, 1);
        board.placeCard(card(Color.RED, Rank.ONE), 1, 1);
        board.placeCard(card(Color.BLUE, Rank.ONE), 2, 1);
        assertTrue(board.isValid());
    }


    @Test
    public void testInvalidFormation() {
        final Board board = new Board();
        board.placeCard(card(Color.BLACK, Rank.ONE), 0, 6);
        board.placeCard(card(Color.RED, Rank.ONE), 18, 6);
        board.placeCard(card(Color.BLUE, Rank.ONE), 19, 6);
        assertFalse(board.isValid());
        assertEquals(2, board.getGroups().size());
    }

    @Test
    public void testCantPlaceCard() {
        final Board board = new Board();
        assertFalse("Should not be able to place card out of bounds", board.placeCard(joker, 0, 7));
    }

}
