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
        if (size() < 3) {
            return false;
        }
        return isValidGroup() || isValidRun();
    }

    private boolean isValidGroup() {
        if (size() > 4) {
            return false;
        }
        if (!Cards.isDifferentColors(cards)) {
            return false;
        }
        return Cards.isSameRanks(cards);
    }

    private boolean isValidRun() {
        if (!Cards.isAllSameColor(cards)) {
            return false;
        }
        return Cards.isAscendingRanks(cards);
    }

    private int size() {
        return cards.size();
    }

}
