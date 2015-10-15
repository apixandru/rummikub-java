package com.github.apixandru.games.rummikub.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.github.apixandru.games.rummikub.model.CardPile;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public final class Main {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final JFrame frame = new JFrame();
		frame.setSize(new Dimension(600, 600));

		final JLayeredPane layeredPane = frame.getLayeredPane();

		final JPanel grid = new JPanel();
		grid.setSize(frame.getSize());

		layeredPane.add(grid, JLayeredPane.DEFAULT_LAYER);

		final CardDndListener listener = new CardDndListener(layeredPane, grid);
		layeredPane.addMouseListener(listener);
		layeredPane.addMouseMotionListener(listener);

		initializeGrid(grid, new CardPile());

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * @param grid
	 * @param cardPile
	 */
	private static void initializeGrid(final JPanel grid, final CardPile cardPile) {
		final int rows = 8;
		final int cols = 8;
		grid.setLayout(new GridLayout(rows, cols));

		boolean first = true;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				final JPanel square = new JPanel(new BorderLayout());
				square.setBorder(new LineBorder(Color.BLACK));
				grid.add(square);
				if (first) {
					first = false;
					final JPanel piece = new CardUi(cardPile.nextCard());
					square.add(piece);
				}
			}
		}
	}

}
