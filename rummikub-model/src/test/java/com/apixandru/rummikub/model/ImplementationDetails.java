package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Color;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.api.Rank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 26, 2015
 */
final class ImplementationDetails {

    private ImplementationDetails() {
    }

    static Player currentPlayer(final Rummikub game) {
        return ((RummikubImpl) game).currentPlayer;
    }

    static Card[][] cloneBoard(final Rummikub game) {
        return Util.copyOf(((RummikubImpl) game).board.getCards());
    }

    static List<Card> endTurnUntilValidGroup(final Player player) {
        while (true) {
            final List<Card> group = getGroup(getCards(player));
            if (null != group) {
                return group;
            }
            player.endTurn();
        }
    }

    static List<Card> getCards(final Player<?> player) {
        return ((PlayerImpl) player).cards;
    }

    static Card getFirstCard(final Player player) {
        return getCards(player).get(0);
    }

    static List<Card> cloneCards(final Player player) {
        return new ArrayList<>(getCards(player));
    }

    static int countCards(final Player player) {
        return getCards(player).size();
    }

    private static List<Card> getGroup(final Collection<Card> cards) {
        final Map<Rank, Map<Color, Card>> cardsByRank = new HashMap<>();
        for (final Card card : cards) {
            final Rank rank = card.getRank();
            Map<Color, Card> cardsForRank = cardsByRank.computeIfAbsent(rank, k -> new HashMap<>());
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
