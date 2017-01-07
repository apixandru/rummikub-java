package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.Color;
import com.apixandru.rummikub.api.game.Rank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Collections.unmodifiableList;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
final class CardGroup {

    private static final Logger log = LoggerFactory.getLogger(CardGroup.class);

    private static final int MIN_NUM_CARDS_IN_GROUP = 3;
    private static final int MAX_NUM_CARDS_IN_GROUP = 4;

    private final List<Card> cards;

    CardGroup(final List<Card> cards) {
        this.cards = unmodifiableList(new ArrayList<>(cards));
    }

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

    private static boolean isDifferentColors(final Collection<Card> cards) {
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

    static boolean isSameRanks(final Collection<Card> cards) {
        return haveSameProperties(cards, Card::getRank);
    }

    static boolean isAllSameColor(final Collection<Card> cards) {
        return haveSameProperties(cards, Card::getColor);
    }

    private static boolean haveSameProperties(final Collection<Card> cards, final Function<Card, ?> function) {
//        if all were the same property then it would return 1, if there were only jokers it would return 0
        return cards
                .stream()
                .map(function)
                .filter(Objects::nonNull)
                .distinct()
                .count() < 2;
    }

    boolean isValid() {
        if (cards.size() < MIN_NUM_CARDS_IN_GROUP) {
            log.debug("{} is invalid. Expecting at least {} cards, found {}", cards, MIN_NUM_CARDS_IN_GROUP, cards.size());
            return false;
        }
        if (isValidGroup()) {
            log.debug("{} is a valid group.", cards);
            return true;
        }
        if (isValidRun()) {
            log.debug("{} is a valid run.", cards);
            return true;
        }
        log.debug("{} is not a valid formation.", cards);
        return false;
    }

    private boolean isValidGroup() {
        return cards.size() <= MAX_NUM_CARDS_IN_GROUP
                && isDifferentColors(cards)
                && isSameRanks(cards);
    }

    private boolean isValidRun() {
        return isAllSameColor(cards) && isAscendingRanks(cards);
    }

}
