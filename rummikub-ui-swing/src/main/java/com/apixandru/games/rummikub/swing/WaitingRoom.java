package com.apixandru.games.rummikub.swing;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 29, 2016
 */
public class WaitingRoom extends JPanel {

    private final JButton btnStart = new JButton("Start");
    private final JToggleButton btnReady = new JToggleButton("Ready");

    private WaitingRoom() {
        super(new BorderLayout());

        final JList<String> list = new JList<>();
        list.setVisibleRowCount(6);
        list.setFixedCellWidth(150);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(createButtonPane(), BorderLayout.EAST);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Waiting room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new WaitingRoom());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WaitingRoom::createAndShowGUI);
    }

    private JPanel createButtonPane() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        buttonPane.add(btnReady, gbc);
        gbc.gridy++;

        gbc.weighty = 1;
        gbc.weightx = 1;
        buttonPane.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;
        gbc.weightx = 0;

        gbc.gridy++;
        buttonPane.add(btnStart, gbc);
        buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        return buttonPane;
    }

}
