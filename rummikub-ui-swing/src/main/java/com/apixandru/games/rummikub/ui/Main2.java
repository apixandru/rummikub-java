package com.apixandru.games.rummikub.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 12, 2015
 */
public class Main2 {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {

            final JFrame frame = new JFrame("Rummikub");

            final Container container = frame.getContentPane();
            container.setLayout(new GridBagLayout());
            container.add(new ScalingGridPanel(7, 20, 60, 96));

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);
        });
    }
}
