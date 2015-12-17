/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public interface DragSource {

    /**
     * @param x
     * @param y
     * @return
     */
    JComponent getComponentAt(final int x, int y);

    /**
     * @param component
     */
    void beginDrag(Component component);

    /**
     * @param component
     */
    void endDrag(Component component);

    /**
     * @param component
     * @return
     */
    Point getPosition(Component component);

}
