package com.github.apixandru.games.rummikub.ui;

import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 12, 2015
 */
public class Main {

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
