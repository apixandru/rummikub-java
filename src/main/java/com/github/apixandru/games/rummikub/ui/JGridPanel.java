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

    private final CardSlot[][] slots;

    /**
     * @param rows
     * @param y
     */
    JGridPanel(final int rows, final int y) {
        slots = new CardSlot[COLS][rows];
        setLayout(new GridLayout(rows, COLS));
        setBounds(0, y == 0 ? 0 : y * TILE_HEIGHT + 60, COLS * TILE_WIDTH, rows * TILE_HEIGHT);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < COLS; j++) {
                final CardSlot slot = new CardSlot(j, i);
                slots[j][i] = slot;
                add(slot);
            }
        }
    }

}
