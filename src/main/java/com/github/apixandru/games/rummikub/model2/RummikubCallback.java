package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface RummikubCallback<H> {

    /**
     * @param card
     * @param hint
     */
    void cardReceived(Card card, H hint);

}
