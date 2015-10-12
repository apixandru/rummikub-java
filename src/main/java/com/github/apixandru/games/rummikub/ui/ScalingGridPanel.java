/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 12, 2015
 */
public final class ScalingGridPanel extends JPanel {


	/**
	 * @param rows
	 * @param cols
	 * @param childW
	 * @param childH
	 */
	public ScalingGridPanel(final int rows, final int cols, final int childW, final int childH) {
		super(new GridLayout(rows, cols));

		final Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < rows; ii++) {
			for (int jj = 0; jj < cols; jj++) {
				final JButton b = new JButton();
				b.setMargin(buttonMargin);
				b.setPreferredSize(new Dimension(childW, childH));
				add(b);
			}
		}

	}

}
