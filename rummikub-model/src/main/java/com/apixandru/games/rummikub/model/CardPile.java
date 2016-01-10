/**
 *
 */
package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Color;
import com.apixandru.games.rummikub.api.Rank;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 20, 2015
 */
final class CardPile {

    final LinkedList<Card> cards = new LinkedList<>();

    {
        final LinkedList<Card> cards = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            for (final Rank rank : Rank.values()) {
                for (final Color color : Color.values()) {
                    cards.add(new Card(color, rank));
                }
            }
            // joker
            cards.add(new Card(null, null));
        }

        setCards(cards);
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
