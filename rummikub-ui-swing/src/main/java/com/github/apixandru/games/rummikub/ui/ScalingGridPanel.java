/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 12, 2015
 */
final class ScalingGridPanel extends JPanel {

    private final double ratio;

    /**
     * @param rows
     * @param cols
     * @param childW
     * @param childH
     */
    public ScalingGridPanel(final int rows, final int cols, final int childW, final int childH) {
        super(new GridLayout(rows, cols));

        this.ratio = computeRatio(rows, cols, childW, childH);

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

    /**
     * @param rows
     * @param cols
     * @param childW
     * @param childH
     * @return
     */
    private static double computeRatio(final int rows, final int cols, final int childW, final int childH) {
        final double width = cols * childW;
        final double height = rows * childH;
        return width / height;
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public final Dimension getPreferredSize() {
        final Dimension d = super.getPreferredSize();
        final Component c = getParent();
        final int parentHeight = c.getHeight();
        if (c.getWidth() < d.getWidth() || parentHeight < d.getHeight()) {
//			we don't want it smaller than the preferred size (minimum)
            return d;
        }
        final int height = (int) (c.getWidth() / this.ratio);
        if (height < parentHeight) {
//			if we allow a size greater than the parent size, it will ignore this directive
            return new Dimension(c.getWidth(), height);
        }
        final int width = (int) (parentHeight * this.ratio);
        return new Dimension(width, parentHeight);
    }

}
