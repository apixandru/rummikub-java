/**
 *
 */
package com.apixandru.utils.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @param <C> the component type
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public class ComponentDragSource<C extends Component> implements DragSource<C> {

    private final Container parent;
    private final Collection<Container> containers;

    /**
     * @param containers the containers involved in the drag and drop
     */
    public ComponentDragSource(final Container... containers) {
        this.parent = containers[0].getParent();
        this.containers = new ArrayList<>(Arrays.asList(containers));
    }

    @Override
    public JComponent getComponentAt(final int x, final int y) {
        for (Container container : containers) {
            final Component componentAt = container.findComponentAt(x - container.getX(), y - container.getY());
            if (null != componentAt) {
                return (JComponent) componentAt;
            }
        }
        return null;
    }

    @Override
    public Point getPosition(final C component) {
        final Container pieceParent = component.getParent();
        final Point parentLocation = pieceParent.getLocation();
        final Point parentParentLocation = pieceParent.getParent().getLocation();
        return new Point(parentLocation.x + parentParentLocation.x, parentLocation.y + parentParentLocation.y);
    }

    @Override
    public void beginDrag(final C component) {
        SwingUtil.addAndNotify(component, this.parent, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void endDrag(final C component) {
        SwingUtil.removeAndNotify(component, this.parent);
    }

}
