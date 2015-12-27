package com.github.apixandru.games.rummikub.ui;

import javax.swing.*;
import java.awt.*;

import static com.github.apixandru.games.rummikub.ui.Main.TILE_HEIGHT;
import static com.github.apixandru.games.rummikub.ui.Main.TILE_WIDTH;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 19, 2015
 */
public class JGridPanel extends JPanel {

    private static final int COLS = 20;
    final CardSlot[][] slots;

    /**
     * @param rows
     * @param offset
     */
    JGridPanel(final int rows, final int offset) {
        slots = new CardSlot[rows][COLS];
        setLayout(new GridLayout(rows, COLS));
        setBounds(0, offset == 0 ? 0 : offset * TILE_HEIGHT + 60, COLS * TILE_WIDTH, rows * TILE_HEIGHT);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < COLS; x++) {
                final CardSlot slot = new CardSlot(x, y);
                slots[y][x] = slot;
                add(slot);
            }
        }
    }

}
