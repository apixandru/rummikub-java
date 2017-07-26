package com.apixandru.rummikub.swing.shared;

import static com.apixandru.rummikub.api.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
public final class RummikubUi {

    private RummikubUi() {
    }

    public static JGridPanel newBoard() {
        return new JGridPanel(NUM_ROWS, 0);
    }

}
