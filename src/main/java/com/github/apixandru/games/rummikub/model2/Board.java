package com.github.apixandru.games.rummikub.model2;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class Board extends Grid {

    /**
     * @param undoManager
     */
    Board(final UndoManager undoManager) {
        super(undoManager, NUM_ROWS, NUM_COLS);

    }

}
