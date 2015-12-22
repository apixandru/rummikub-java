package com.github.apixandru.games.rummikub.model.listeners;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 22, 2015
 */
public interface CardLocationListener {

    /**
     * @param card
     * @param x
     * @param y
     */
    void onCardPlaced(Card card, int x, int y);

}
