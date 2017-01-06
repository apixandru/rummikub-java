package com.apixandru.rummikub.model.game;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.Color;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.Rank;

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

    private ImplementationDetails() {
    }

    public static Player currentPlayer(final Rummikub game) {
        return ((RummikubImpl) game).currentPlayer;
    }

    public static Card[][] cloneBoard(final Rummikub game) {
        return Util.copyOf(((RummikubImpl) game).board.cards);
    }

    public static List<Card> endTurnUntilValidGroup(final Player player) {
        while (true) {
            final List<Card> group = getGroup(getCards(player));
            if (null != group) {
                return group;
            }
            player.endTurn();
        }
    }

    public static List<Card> getCards(final Player<?> player) {
        return ((PlayerImpl) player).cards;
    }

    public static Card getFirstCard(final Player player) {
        return getCards(player).get(0);
    }

    public static List<Card> cloneCards(final Player player) {
        return new ArrayList<>(getCards(player));
    }

    public static int countCards(final Player player) {
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
