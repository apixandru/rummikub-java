package com.github.apixandru.games.rummikub;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model2.ImplementationDetails;
import com.github.apixandru.games.rummikub.model2.Player;
import com.github.apixandru.games.rummikub.model2.Rummikub;
import com.github.apixandru.games.rummikub.model2.RummikubFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public final class RummikubTest2 {

    private Rummikub rummikub;
    private Player player;

    @Before
    public void setup() {
        this.rummikub = RummikubFactory.newInstance();
        this.player = rummikub.addPlayer("Player 1");
    }


    @Test
    public void testGame() {
        final Player player2 = rummikub.addPlayer("Johnny");

        assertSame(player, ImplementationDetails.currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, ImplementationDetails.currentPlayer(rummikub));

        player.endTurn();
        assertSame(player2, ImplementationDetails.currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, ImplementationDetails.currentPlayer(rummikub));
    }

    @Test
    public void testPlaceCardOnBoard() {
        final int numberOfCardsBeforeEndTurn = ImplementationDetails.countCards(player);
        player.endTurn();
        assertEquals(numberOfCardsBeforeEndTurn + 1, ImplementationDetails.countCards(player));
    }

    @Test
    public void testCommitBoard() {
        final List<Card> group = ImplementationDetails.endTurnUntilValidGroup(player);
        placeCardsOnFirstSlots(group, player);
        final List<Card> cardsBeforeEndTurn = ImplementationDetails.cloneCards(player);
        player.endTurn();
        assertEquals(cardsBeforeEndTurn, ImplementationDetails.getCards(player));
    }

    @Test
    public void testPutCardOnBoard() {
        final Card card = ImplementationDetails.getFirstCard(player);
        final Card[][] cards = ImplementationDetails.cloneBoard(rummikub);
        player.placeCardOnBoard(card, 0, 0);
        assertFalse(Arrays.deepEquals(ImplementationDetails.cloneBoard(rummikub), cards));
    }

    @Test
    public void testUndo() {
        final Card card = ImplementationDetails.getFirstCard(player);
        final Card[][] cards = ImplementationDetails.cloneBoard(rummikub);
        player.placeCardOnBoard(card, 0, 0);
        player.endTurn();
        assertTrue(Arrays.deepEquals(ImplementationDetails.cloneBoard(rummikub), cards));
    }

    @Test
    public void testTakeCardFromBoard() {
        final Card card = ImplementationDetails.getFirstCard(player);
        final Card[][] cards = ImplementationDetails.cloneBoard(rummikub);
        player.placeCardOnBoard(card, 0, 0);
        player.takeCardFromBoard(card, 0, 0);
        assertTrue(Arrays.deepEquals(ImplementationDetails.cloneBoard(rummikub), cards));
    }

    @Test
    public void testCannotTakeCardAfterEnd() {
        final List<Card> group = ImplementationDetails.endTurnUntilValidGroup(player);
        placeCardsOnFirstSlots(group, player);
        player.endTurn();

        final Card[][] cards = ImplementationDetails.cloneBoard(rummikub);
        player.takeCardFromBoard(group.get(0), 0, 0);
        assertTrue(Arrays.deepEquals(ImplementationDetails.cloneBoard(rummikub), cards));
    }

    @Test
    public void testLessCardsAfterPlace() {
        final int numCardsBeforePlace = ImplementationDetails.countCards(player);
        final Card card = ImplementationDetails.getFirstCard(player);
        player.placeCardOnBoard(card, 0, 0);
        assertEquals(numCardsBeforePlace - 1, ImplementationDetails.countCards(player));
    }

    /**
     * @param cards
     * @param player
     */
    private static void placeCardsOnFirstSlots(final List<Card> cards, final Player player) {
        for (int i = 0; i < cards.size(); i++) {
            player.placeCardOnBoard(cards.get(i), i, 0);
        }
    }

}
