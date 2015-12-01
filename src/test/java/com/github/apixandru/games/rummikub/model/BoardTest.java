package com.github.apixandru.games.rummikub.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexandru-Constantin Bledea
 * @since November 24, 2015
 */
public class BoardTest {

    @Test
    public void testValidFormation() {
        final Board board = new Board();
        board.placeCard(new Card(Color.BLACK, Rank.ONE), 0, 1);
        board.placeCard(new Card(Color.RED, Rank.ONE), 1, 1);
        board.placeCard(new Card(Color.BLUE, Rank.ONE), 2, 1);
        Assert.assertTrue(board.isValid());
    }


    @Test
    public void testInvalidFormation() {
        final Board board = new Board();
        board.placeCard(new Card(Color.BLACK, Rank.ONE), 0, 1);
        board.placeCard(new Card(Color.RED, Rank.ONE), 3, 1);
        board.placeCard(new Card(Color.BLUE, Rank.ONE), 2, 1);
        Assert.assertFalse(board.isValid());
        Assert.assertEquals(2, board.getGroups().size());
    }
}
