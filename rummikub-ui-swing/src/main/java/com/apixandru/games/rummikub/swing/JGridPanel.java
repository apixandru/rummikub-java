package com.apixandru.games.rummikub.swing;

import javax.swing.JPanel;
import java.awt.GridLayout;

import static com.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static com.apixandru.games.rummikub.swing.UiConstants.TILE_HEIGHT;
import static com.apixandru.games.rummikub.swing.UiConstants.TILE_WIDTH;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 19, 2015
 */
class JGridPanel extends JPanel {

    final CardSlot[][] slots;

    JGridPanel(final int rows, final int offset) {
        slots = new CardSlot[rows][NUM_COLS];
        setLayout(new GridLayout(rows, NUM_COLS));
        setBounds(0, offset == 0 ? 0 : offset * TILE_HEIGHT + 60, NUM_COLS * TILE_WIDTH, rows * TILE_HEIGHT);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                final CardSlot slot = new CardSlot(x, y);
                slots[y][x] = slot;
                add(slot);
            }
        }
    }

}
