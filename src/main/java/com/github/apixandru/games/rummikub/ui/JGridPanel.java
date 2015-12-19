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

    /**
     * @param rows
     * @param y
     */
    JGridPanel(final int rows, final int y) {
        final int cols = 20;
        setLayout(new GridLayout(rows, cols));
        setBounds(0, y == 0 ? 0 : y * TILE_HEIGHT + 60, cols * TILE_WIDTH, rows * TILE_HEIGHT);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                add(new CardSlot(j, i));
            }
        }
    }

}
