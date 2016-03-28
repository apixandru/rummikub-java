package com.apixandru.games.rummikub.swing;

import static com.apixandru.games.rummikub.api.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
final class RummikubUi {

    private RummikubUi() {
    }

    static JGridPanel newBoard() {
        return new JGridPanel(NUM_ROWS, 0);
    }

}
