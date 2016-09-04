package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.game.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 07, 2016
 */
interface MoveHelper {

    boolean canInteractWithBoard();

    boolean canTakeCardFromBoard(Card card);

}
