package com.github.apixandru.games.rummikub.model2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public final class RummikubTest2 {

    @Test
    public void testGame() {
        final RummikubImpl game = new RummikubImpl();
        final Player first = game.addPlayer("John");
        final Player second = game.addPlayer("Johnny");

        assertSame(first, game.currentPlayer());

        second.endTurn();
        assertSame(first, game.currentPlayer());

        first.endTurn();
        assertSame(second, game.currentPlayer());

        second.endTurn();
        assertSame(first, game.currentPlayer());
    }

    @Test
    public void testPlaceCardOnBoard() {
        final Rummikub game = new RummikubImpl();
        final PlayerImpl player = (PlayerImpl) game.addPlayer("John");

        final int numberOfCardsBeforeEndTurn = player.cards.size();
        player.endTurn();
        assertEquals(numberOfCardsBeforeEndTurn + 1, player.cards.size());
    }

}
