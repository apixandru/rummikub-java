package com.apixandru.rummikub.swing.shared;

import com.apixandru.rummikub.api.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 07, 2016
 */
public interface MoveHelper {

    boolean canInteractWithBoard();

    boolean canTakeCardFromBoard(Card card);

}
