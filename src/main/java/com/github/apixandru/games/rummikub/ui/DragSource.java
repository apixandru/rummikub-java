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
interface DragSource {

    /**
     * @param x
     * @param y
     * @return
     */
    JComponent getComponentAt(final int x, int y);

    /**
     * @param component
     */
    void beginDrag(CardUi component);

    /**
     * @param component
     */
    void endDrag(CardUi component);

    /**
     * @param component
     * @return
     */
    Point getPosition(CardUi component);

}
