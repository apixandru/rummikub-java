package com.apixandru.games.rummikub.swing;

import javax.swing.*;
import java.awt.*;

import static com.apixandru.games.rummikub.api.Constants.NUM_COLS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 19, 2015
 */
class JGridPanel extends JPanel {

    final CardSlot[][] slots;

    /**
     * @param rows
     * @param offset
     */
    JGridPanel(final int rows, final int offset) {
        slots = new CardSlot[rows][NUM_COLS];
        setLayout(new GridLayout(rows, NUM_COLS));
        setBounds(0, offset == 0 ? 0 : offset * Main.TILE_HEIGHT + 60, NUM_COLS * Main.TILE_WIDTH, rows * Main.TILE_HEIGHT);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                final CardSlot slot = new CardSlot(x, y);
                slots[y][x] = slot;
                add(slot);
            }
        }
    }

}