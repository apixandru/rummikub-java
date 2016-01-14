/**
 *
 */
package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Constants;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 20, 2015
 */
final class CardPile {

    private final LinkedList<Card> cards = new LinkedList<>();

    {
        setCards(Constants.CARDS);
    }

    /**
     * @return
     */
    int getNumberOfCards() {
        return this.cards.size();
    }

    /**
     * @return
     */
    boolean hasMoreCards() {
        return !this.cards.isEmpty();
    }

    /**
     * @return
     */
    Card nextCard() {
        return this.cards.remove();
    }

    /**
     * @param cards
     */
    void setCards(final Collection<Card> cards) {
        this.cards.addAll(cards);
        Collections.shuffle(this.cards);
    }

}
