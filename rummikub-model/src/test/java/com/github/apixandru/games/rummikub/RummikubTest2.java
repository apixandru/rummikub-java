package com.github.apixandru.games.rummikub;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.ImplementationDetails;
import com.github.apixandru.games.rummikub.model.Player;
import com.github.apixandru.games.rummikub.model.Rummikub;
import com.github.apixandru.games.rummikub.model.RummikubFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.apixandru.games.rummikub.model.ImplementationDetails.cloneCards;
import static com.github.apixandru.games.rummikub.model.ImplementationDetails.currentPlayer;
import static com.github.apixandru.games.rummikub.model.ImplementationDetails.getCards;
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
    private Player<Object> player;

    @Before
    public void setup() {
        this.rummikub = RummikubFactory.newInstance();
        this.player = rummikub.addPlayer("Player 1", null);
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

    /**
     * @param cards
     */
    private void assertCardsOnBoard(final Card[][] cards) {
        assertTrue(Arrays.deepEquals(cards, cardsOnBoard()));
    }

    /**
     * Only the current player can end the turn.
     * Requests from other players to end the turn should be ignored.
     */
    @Test
    public void testGame() {
        final Player player2 = rummikub.addPlayer("Johnny", null);

        assertSame(player, currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, currentPlayer(rummikub));

        player.endTurn();
        assertSame(player2, currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, currentPlayer(rummikub));
    }

    /**
     * Each player should receive 14 cards.
     */
    @Test
    public void testPlayerReceivesCard() {
        final Player player2 = rummikub.addPlayer("Johnny", null);

        assertEquals(14, ImplementationDetails.countCards(player));
        assertEquals(14, ImplementationDetails.countCards(player2));
    }

    /**
     * If you haven't done anything in a turn, you are given a card.
     */
    @Test
    public void testPlaceCardOnBoard() {
        final int numberOfCardsBeforeEndTurn = countCards();
        player.endTurn();
        assertEquals(numberOfCardsBeforeEndTurn + 1, countCards());
    }

    /**
     * After a successful commit you are not given a card.
     */
    @Test
    public void testCommitBoard() {
        final List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        final List<Card> cardsBeforeEndTurn = cloneCards(player);
        player.endTurn();
        assertEquals(cardsBeforeEndTurn, getCards(player));
    }

    /**
     * The board should be different as soon as the card was placed on it.
     */
    @Test
    public void testPutCardOnBoard() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();
        player.placeCardOnBoard(card, 0, 0);
        assertFalse(Arrays.deepEquals(cardsOnBoard(), cards));
    }

    /**
     * If the board is in a invalid state then the state should roll back.
     */
    @Test
    public void testUndo() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();
        player.placeCardOnBoard(card, 0, 0);
        player.endTurn();
        assertCardsOnBoard(cards);
    }

    /**
     * If the cards on the board aren't locked, you should be able to take them in hand.
     */
    @Test
    public void testTakeCardFromBoard() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();
        player.placeCardOnBoard(card, 0, 0);
        player.takeCardFromBoard(card, 0, 0, null);
        assertCardsOnBoard(cards);
    }

    /**
     * After a commit all cards are locked on the board.
     * You shouldn't be able to take locked cards in hand.
     */
    @Test
    public void testCannotTakeCardAfterEnd() {
        final List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        player.endTurn();

        final Card[][] cards = cardsOnBoard();
        player.takeCardFromBoard(group.get(0), 0, 0, null);
        assertTrue(Arrays.deepEquals(cardsOnBoard(), cards));
    }

    /**
     * The player should have one less card after placing a card on the board.
     */
    @Test
    public void testLessCardsAfterPlace() {
        final int numCardsBeforePlace = countCards();
        final Card card = getFirstCard();
        player.placeCardOnBoard(card, 0, 0);
        assertEquals(numCardsBeforePlace - 1, countCards());
    }

    /**
     * Just moving cards on the board when it's your turn is not enough.
     * The changes should rollback and you should get a card.
     */
    @Test
    public void testMoveBoardCardsRollback() {
        final List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        player.endTurn();

        final Card firstCardOnBoard = group.get(0);

        final Card[][] cardsOnBoard = cardsOnBoard();
        final int cardsInHand = countCards();

        player.moveCardOnBoard(firstCardOnBoard, 0, 0, group.size(), 0);
        player.endTurn();

        assertCardsOnBoard(cardsOnBoard);
        assertEquals(cardsInHand + 1, countCards());
    }

    /**
     * You shouldn't be able to place a card out of bounds.
     */
    @Test
    public void testPlaceCardOutOfBounds() {
        final Card[][] cardsOnBoard = cardsOnBoard();
        final Card card = getFirstCard();
        player.placeCardOnBoard(card, 0, 7);
        assertCardsOnBoard(cardsOnBoard);
    }

}
