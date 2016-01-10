/**
 *
 */
package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Color;
import com.apixandru.games.rummikub.api.Rank;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
final class Cards {

    /**
     *
     */
    private Cards() {
    }

    /**
     * @return
     */
    static boolean isDifferentColors(final Collection<Card> cards) {
        final Collection<Color> colors = new HashSet<>();
        for (final Card card : cards) {
            final Color cardColor = card.getColor();
            if (null == cardColor) {
                continue;
            }
            if (colors.contains(cardColor)) {
                return false;
            }
            colors.add(cardColor);
        }
        return true;
    }

    /**
     * @param cards
     * @return
     */
    static boolean isSameRanks(final Collection<Card> cards) {
        return haveSameProperties(cards, Card::getRank);
    }

    /**
     * @return
     */
    static boolean isAllSameColor(final Collection<Card> cards) {
        return haveSameProperties(cards, Card::getColor);
    }

    /**
     * @param cards
     * @param function
     * @return
     */
    private static boolean haveSameProperties(final Collection<Card> cards, final Function<Card, ?> function) {
//        if all were the same property then it would return 1, if there were only jokers it would return 0
        return cards
                .stream()
                .map(function)
                .filter(Objects::nonNull)
                .distinct()
                .count() < 2;
    }

    /**
     * @param cards
     * @return
     */
    static boolean isAscendingRanks(final List<Card> cards) {
        Rank expected = null;
        boolean first = true;
        for (int i = 0, to = cards.size(); i < to; i++) {
            final Card card = cards.get(i);
            final Rank rank = card.getRank();
            if (first && null != rank) {
                if (!isValidRankInRun(rank, i)) {
                    return false;
                }
                expected = rank;
                first = false;
            }
//            if rank is null then joker, matches
            if (rank != null && expected != rank) {
                return false;
            }
            expected = next(expected);
        }
        return true;
    }


    /**
     * @return
     */
    private static Rank next(final Rank rank) {
        final Rank[] values = Rank.values();
        if (null == rank || rank.ordinal() >= values.length - 1) {
            return null;
        }
        return values[rank.ordinal() + 1];
    }

    /**
     * Because the run can start with one or two jokers, we need to infer the
     * rank of the cards that they substitute. This means that the rank in a run
     * can never be lower than the card number in that run
     *
     * @param rank            the rank of the card
     * @param cardNumberInRun the number of the card in the group
     * @return true if the card rank is valid for the position in the run
     */
    private static boolean isValidRankInRun(final Rank rank, final int cardNumberInRun) {
        return rank.ordinal() >= cardNumberInRun;
    }

}
