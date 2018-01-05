package com.apixandru.rummikub.swing.shared;

import com.apixandru.rummikub.brotocol.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.room.StartGameListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NORTHWEST;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 29, 2016
 */
public final class WaitingRoomPanel extends JPanel implements RummikubRoomListener {

    private final DefaultListModel<String> playerListModel = new DefaultListModel<>();

    private final StartGameListener startGameListener;

    public WaitingRoomPanel(final StartGameListener startGameListener) {
        super(new BorderLayout());

        this.startGameListener = startGameListener;

        final JList<String> list = new JList<>(playerListModel);
        list.setVisibleRowCount(6);
        list.setFixedCellWidth(150);

        add(new JScrollPane(list), CENTER);
        add(createButtonPane(), EAST);
    }

    public static JFrame createAndShowGui(final WaitingRoomPanel waitingRoomPanel) {
        JFrame frame = new JFrame("Waiting room");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(waitingRoomPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    private JPanel createButtonPane() {
        final JButton btnStart = new JButton("Start");
        btnStart.addActionListener(e -> startGameListener.startGame());

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = NORTHWEST;
        gbc.fill = HORIZONTAL;

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

    @Override
    public void playerJoined(final String playerName) {
        invokeLater(() -> playerListModel.addElement(playerName));
    }

    @Override
    public void playerLeft(final String playerName) {
        invokeLater(() -> playerListModel.removeElement(playerName));
    }

}
