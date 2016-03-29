package com.apixandru.games.rummikub.swing;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 29, 2016
 */
public class WaitingRoom extends JPanel {

    private WaitingRoom() {
        super(new BorderLayout());

        final JList<String> list = new JList<>();
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        final JButton btnStart = new JButton("Start");
        final JToggleButton btnReady = new JToggleButton("Ready");

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(btnReady);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(btnStart);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
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

}
