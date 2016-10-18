package com.apixandru.rummikub.swing.util;

import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Point;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public interface DragSource<C extends Component> {

    JComponent getComponentAt(final int x, int y);

    void beginDrag(C component);

    void endDrag(C component);

    Point getPosition(C component);

}