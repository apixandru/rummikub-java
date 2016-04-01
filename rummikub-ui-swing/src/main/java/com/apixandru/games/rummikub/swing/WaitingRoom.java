package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.brotocol.connect.WaitingRoomListener;

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

/**
 * @author Alexandru-Constantin Bledea
 * @since March 29, 2016
 */
public class WaitingRoom extends JPanel implements WaitingRoomListener {

    private final JButton btnStart = new JButton("Start");

    private final DefaultListModel<String> playerListModel = new DefaultListModel<>();

    private WaitingRoom() {
        super(new BorderLayout());

        final JList<String> list = new JList<>(playerListModel);
        list.setVisibleRowCount(6);
        list.setFixedCellWidth(150);

        add(new JScrollPane(list), CENTER);
        add(createButtonPane(), EAST);
    }

    public static WaitingRoomListener createAndShowGUI() {
        JFrame frame = new JFrame("Waiting room");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final WaitingRoom contentPane = new WaitingRoom();
        frame.setContentPane(contentPane);

        frame.pack();
        frame.setVisible(true);

        return contentPane;

    }

    private JPanel createButtonPane() {
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
        playerListModel.addElement(playerName);
    }

    @Override
    public void playerLeft(final String playerName) {
        playerListModel.removeElement(playerName);
    }

}
