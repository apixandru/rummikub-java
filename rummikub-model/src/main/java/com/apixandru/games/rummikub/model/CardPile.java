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

    int getNumberOfCards() {
        return this.cards.size();
    }

    boolean hasMoreCards() {
        return !this.cards.isEmpty();
    }

    Card nextCard() {
        return this.cards.remove();
    }

    void setCards(final Collection<Card> cards) {
        this.cards.addAll(cards);
        Collections.shuffle(this.cards);
    }

}
