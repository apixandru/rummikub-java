package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Board {

    /**
     *
     */
    void lockPieces();

    /**
     * @return
     */
    boolean isValid();

}
