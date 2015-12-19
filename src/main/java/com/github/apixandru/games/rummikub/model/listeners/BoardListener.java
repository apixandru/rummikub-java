package com.github.apixandru.games.rummikub.model.listeners;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public interface BoardListener {

    /**
     * @param card
     * @param x
     * @param y
     */
    void onCardPlacedOnTable(Card card, int x, int y);

}
