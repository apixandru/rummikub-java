package com.github.apixandru.games.rummikub.model2;

import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public final class RummikubTest2 {

    @Test
    public void testGame() {
        final Rummikub game = new RummikubImpl();
        final Player first = game.addPlayer("John");
        final Player second = game.addPlayer("Johnny");

        assertSame(first, game.currentPlayer());
    }

    @Test
    public void testPlaceCardOnBoard() {
        final Rummikub game = new RummikubImpl();
        final Player first = game.addPlayer("John");
        final Player second = game.addPlayer("Johnny");

    }

}
