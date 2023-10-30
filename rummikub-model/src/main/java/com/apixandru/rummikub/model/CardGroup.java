package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Color;
import com.apixandru.rummikub.api.Rank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;

import static java.util.Collections.unmodifiableList;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
final class CardGroup {

    private static final List<Rank> validRanks = Arrays.asList(
            Rank.ONE,
            Rank.TWO,
            Rank.THREE,
            Rank.FOUR,
            Rank.FIVE,
            Rank.SIX,
            Rank.SEVEN,
            Rank.EIGHT,
            Rank.NINE,
            Rank.TEN,
            Rank.ELEVEN,
            Rank.TWELVE,
            Rank.THIRTEEN,
            Rank.ONE
    );

    private static final Logger log = LoggerFactory.getLogger(CardGroup.class);

    private static final int MIN_NUM_CARDS_IN_GROUP = 3;
    private static final int MAX_NUM_CARDS_IN_GROUP = 4;

    private final List<Card> cards;

    CardGroup(List<Card> cards) {
        this.cards = unmodifiableList(new ArrayList<>(cards));
    }

    static boolean isAscendingRanks(final List<Card> cards) {
        int indexFirstNotJoker = findFirstNotJoker(cards);
        if (indexFirstNotJoker == -1) {
            return false;
        }

        int startIndex = findFirst(cards, indexFirstNotJoker);
        if (startIndex < 0) {
            return false;
        }

        for (int i = 0, to = cards.size(); i < to; i++) {
            Card currentCard = cards.get(i);
            Rank currentRank = currentCard.getRank();
            int expectedIndex = startIndex + i;
            if (expectedIndex >= validRanks.size()) {
                return false;
            }
            Rank expectedRank = validRanks.get(expectedIndex);
            if (!rankMatches(currentRank, expectedRank)) {
                return false;
            }
        }
        return true;
    }

    private static int findFirst(List<Card> cards, int indexFirstNotJoker) {
        Card firstCardNotJoker = cards.get(indexFirstNotJoker);
        if (indexFirstNotJoker == 0 && firstCardNotJoker.getRank() == Rank.ONE) {
            return 0;
        }
        int rankIndex = validRanks.lastIndexOf(firstCardNotJoker.getRank());
        return rankIndex - indexFirstNotJoker;
    }

    private static boolean rankMatches(Rank currentRank, Rank expectedRank) {
        if (currentRank == null || expectedRank == null) {
            return true;
        }
        return currentRank == expectedRank;
    }

    private static int findFirstNotJoker(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (!isJoker(card)) {
                return i;
            }
        }
        return -1;
    }

    private static Rank next(final Rank rank) {
        final Rank[] values = Rank.values();
        if (null == rank || rank.ordinal() >= values.length - 1) {
            return null;
        }
        return values[rank.ordinal() + 1];
    }

    private static boolean isJoker(Card card) {
        return card.getColor() == null && card.getRank() == null;
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

    boolean isValidRun() {
        return isAllSameColor(cards) && isAscendingRanks(cards);
    }

}
