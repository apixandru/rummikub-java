package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Color;
import com.github.apixandru.games.rummikub.model.Rank;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testCommitBoard() {
        final RummikubImpl rummikub = new RummikubImpl();
        final PlayerImpl player = (PlayerImpl) rummikub.addPlayer("Player");
        final List<Card> group = endTurnUntilValidGroup(player);
        for (int i = 0; i < group.size(); i++) {
            player.placeCardOnBoard(group.get(i), i, 0);
        }
        final List<Card> cardsBeforeEndTurn = new ArrayList<>(player.cards);
        player.endTurn();
        assertEquals(cardsBeforeEndTurn, player.cards);
    }

    /**
     * @param player
     * @return
     */
    private List<Card> endTurnUntilValidGroup(final PlayerImpl player) {
        while (true) {
            final List<Card> group = getGroup(player.cards);
            if (null != group) {
                return group;
            }
            player.endTurn();
        }
    }

    /**
     * @param cards
     * @return
     */
    private static List<Card> getGroup(final Collection<Card> cards) {
        final Map<Rank, Map<Color, Card>> cardsByRank = new HashMap<>();
        for (final Card card : cards) {
            final Rank rank = card.getRank();
            Map<Color, Card> cardsForRank = cardsByRank.get(rank);
            if (null == cardsForRank) {
                cardsForRank = new HashMap<>();
                cardsByRank.put(rank, cardsForRank);
            }
            cardsForRank.put(card.getColor(), card);
        }
        for (Map<Color, Card> entry : cardsByRank.values()) {
            final Collection<Card> values = entry.values();
            if (values.size() > 2) {
                return new ArrayList<>(values);
            }
        }
        return null;
    }

}
