/**
 *
 */
package com.apixandru.utils.swing;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public interface DragSource<C extends Component> {

    /**
     * @param x the x position
     * @param y the y position
     * @return the component at that position
     */
    JComponent getComponentAt(final int x, int y);

    /**
     * @param component the component
     */
    void beginDrag(C component);

    /**
     * @param component the component
     */
    void endDrag(C component);

    /**
     * @param component the component
     * @return the position of the component
     */
    Point getPosition(C component);

}
