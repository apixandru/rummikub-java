package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 07, 2016
 */
interface MoveHelper {

    /**
     * @return
     */
    boolean canInteractWithBoard();

    /**
     * @param card
     * @return
     */
    boolean canTakeCardFromBoard(Card card);

}
