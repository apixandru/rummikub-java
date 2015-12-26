package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Color;
import com.github.apixandru.games.rummikub.model.Rank;
import com.github.apixandru.games.rummikub.model.RummikubGame;
import com.github.apixandru.games.rummikub.model.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 26, 2015
 */
public final class ImplementationDetails {

    /**
     *
     */
    private ImplementationDetails() {
    }

    /**
     * @param game
     * @return
     */
    public static Player currentPlayer(final Rummikub game) {
        return ((RummikubImpl) game).currentPlayer;
    }

    /**
     * @param game
     * @return
     */
    public static Card[][] cloneBoard(final Rummikub game) {
        return Util.copyOf(((RummikubImpl)game).board.cards);
    }

    /**
     * @param player
     * @return
     */
    public static List<Card> endTurnUntilValidGroup(final Player player) {
        while (true) {
            final List<Card> group = getGroup(getCards(player));
            if (null != group) {
                return group;
            }
            player.endTurn();
        }
    }

    /**
     * @param player
     * @return
     */
    public static List<Card> getCards(final Player player) {
        return ((PlayerImpl) player).cards;
    }

    /**
     * @param player
     * @return
     */
    public static Card getFirstCard(final Player player) {
        return getCards(player).get(0);
    }

    /**
     * @param player
     * @return
     */
    public static List<Card> cloneCards(final Player player) {
        return new ArrayList<>(getCards(player));
    }

    /**
     * @param player
     * @return
     */
    public static int countCards(final Player player) {
        return getCards(player).size();
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
