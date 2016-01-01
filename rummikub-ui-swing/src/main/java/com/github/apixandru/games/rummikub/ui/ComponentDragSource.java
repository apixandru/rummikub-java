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
final class ComponentDragSource implements DragSource {

    private final Container parent;
    private final Container[] containers;

    /**
     * @param containers
     */
    public ComponentDragSource(final Container... containers) {
        this.parent = containers[0].getParent();
        this.containers = containers;
    }

    /* (non-Javadoc)
     * @see com.github.apixandru.games.rummikub.ui.DragSource#getComponentAt(int, int)
     */
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
    public Point getPosition(final CardUi component) {
        final Container pieceParent = component.getParent();
        final Point parentLocation = pieceParent.getLocation();
        final Point parentParentLocation = pieceParent.getParent().getLocation();
        return new Point(parentLocation.x + parentParentLocation.x, parentLocation.y + parentParentLocation.y);
    }

    /* (non-Javadoc)
     * @see com.github.apixandru.games.rummikub.ui.DragSource#beginDrag(java.awt.Component)
     */
    @Override
    public void beginDrag(final CardUi component) {
        this.parent.add(component, JLayeredPane.DRAG_LAYER);
    }

    /* (non-Javadoc)
     * @see com.github.apixandru.games.rummikub.ui.DragSource#endDrag(java.awt.Component)
     */
    @Override
    public void endDrag(final CardUi component) {
        this.parent.remove(component);
        this.parent.repaint();
    }

}
