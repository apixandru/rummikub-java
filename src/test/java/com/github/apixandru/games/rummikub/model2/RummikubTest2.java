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
        Rummikub game = new RummikubImpl();
        Player first = game.addPlayer("John");
        Player second = game.addPlayer("Johnny");
        assertSame(first, game.currentPlayer());
    }
}
