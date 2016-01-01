package com.github.apixandru.games.rummikub.model2;

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

    @Test
    public void testValidFormation() {
        board.placeCard(card(BLACK, ONE), 0, 1);
        board.placeCard(card(RED, ONE), 1, 1);
        board.placeCard(card(BLUE, ONE), 2, 1);
        assertTrue(board.isValid());
    }

    @Test
    public void testInvalidFormation() {
        board.placeCard(card(BLACK, ONE), 0, 6);
        board.placeCard(card(RED, ONE), 18, 6);
        board.placeCard(card(BLUE, ONE), 19, 6);
        assertFalse(board.isValid());
        assertEquals(2, board.streamGroups().count());
    }

}
