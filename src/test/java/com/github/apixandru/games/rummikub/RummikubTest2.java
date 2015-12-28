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

import static com.github.apixandru.games.rummikub.model2.ImplementationDetails.cloneCards;
import static com.github.apixandru.games.rummikub.model2.ImplementationDetails.currentPlayer;
import static com.github.apixandru.games.rummikub.model2.ImplementationDetails.getCards;
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


    /**
     * @param cards
     */
    @Utility
    private void placeCardsOnFirstSlots(final List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            this.player.placeCardOnBoard(cards.get(i), i, 0);
        }
    }

    /**
     * @return
     */
    @Utility
    private int countCards() {
        return ImplementationDetails.countCards(this.player);
    }

    /**
     * @return
     */
    @Utility
    private Card[][] cardsOnBoard() {
        return ImplementationDetails.cloneBoard(this.rummikub);
    }

    /**
     * @return
     */
    @Utility
    private Card getFirstCard() {
        return ImplementationDetails.getFirstCard(this.player);
    }

    /**
     * @return
     */
    @Utility
    private List<Card> endTurnUntilValidGroup() {
        return ImplementationDetails.endTurnUntilValidGroup(this.player);
    }


    @Test
    public void testGame() {
        final Player player2 = rummikub.addPlayer("Johnny");

        assertSame(player, currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, currentPlayer(rummikub));

        player.endTurn();
        assertSame(player2, currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, currentPlayer(rummikub));
    }

    @Test
    public void testPlaceCardOnBoard() {
        final int numberOfCardsBeforeEndTurn = countCards();
        player.endTurn();
        assertEquals(numberOfCardsBeforeEndTurn + 1, countCards());
    }

    @Test
    public void testCommitBoard() {
        final List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        final List<Card> cardsBeforeEndTurn = cloneCards(player);
        player.endTurn();
        assertEquals(cardsBeforeEndTurn, getCards(player));
    }

    @Test
    public void testPutCardOnBoard() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();
        player.placeCardOnBoard(card, 0, 0);
        assertFalse(Arrays.deepEquals(cardsOnBoard(), cards));
    }

    @Test
    public void testUndo() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();
        player.placeCardOnBoard(card, 0, 0);
        player.endTurn();
        assertTrue(Arrays.deepEquals(cardsOnBoard(), cards));
    }

    @Test
    public void testTakeCardFromBoard() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();
        player.placeCardOnBoard(card, 0, 0);
        player.takeCardFromBoard(card, 0, 0);
        assertTrue(Arrays.deepEquals(cardsOnBoard(), cards));
    }

    @Test
    public void testCannotTakeCardAfterEnd() {
        final List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        player.endTurn();

        final Card[][] cards = cardsOnBoard();
        player.takeCardFromBoard(group.get(0), 0, 0);
        assertTrue(Arrays.deepEquals(cardsOnBoard(), cards));
    }

    @Test
    public void testLessCardsAfterPlace() {
        final int numCardsBeforePlace = countCards();
        final Card card = getFirstCard();
        player.placeCardOnBoard(card, 0, 0);
        assertEquals(numCardsBeforePlace - 1, countCards());
    }

}
