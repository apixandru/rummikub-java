/**
 *
 */
package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
final class CardGroup {

    private final List<Card> cards;

    public CardGroup(final List<Card> cards) {
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
    }

    public boolean isValid() {
        return size() >= 3
                && (isValidGroup() || isValidRun());
    }

    private boolean isValidGroup() {
        return size() <= 4
                && Cards.isDifferentColors(cards)
                && Cards.isSameRanks(cards);
    }

    private boolean isValidRun() {
        return Cards.isAllSameColor(cards) && Cards.isAscendingRanks(cards);
    }

    private int size() {
        return cards.size();
    }

}
