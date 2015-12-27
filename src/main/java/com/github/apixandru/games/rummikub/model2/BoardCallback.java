package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface BoardCallback {

    /**
     * @param card
     * @param x
     * @param y
     */
    void cardPlacedOnBoard(Card card, int x, int y);

}
