package com.apixandru.utils.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 16, 2016
 */
public abstract class AbstractDndListener<C extends Component, P extends Component> extends MouseAdapter {

    protected final DragSource<C> dragSource;
    private final Class<C> componentClass;

    protected P draggablePieceParent;
    protected C draggablePiece;

    private int xOffset;
    private int yOffset;

    /**
     * @param componentClass draggable type class
     * @param dragSource     the drag source
     */
    protected AbstractDndListener(final Class<C> componentClass, final DragSource<C> dragSource) {
        this.dragSource = dragSource;
        this.componentClass = componentClass;
    }

    /**
     * @param event mouse event
     * @return component
     */
    protected final JComponent getComponentAt(final MouseEvent event) {
        return this.dragSource.getComponentAt(event.getX(), event.getY());
    }


    /**
     * @param event mouse event
     * @return draggable
     */
    @SuppressWarnings("unchecked")
    protected final C getDraggable(final MouseEvent event) {
        Component c = getComponentAt(event);
        while (null != c && !(componentClass.isInstance(c))) {
            c = c.getParent();
        }
        return (C) c;
    }

    /**
     * @param event mouse event
     */
    protected final void updateMovingPieceLocation(final MouseEvent event) {
        this.draggablePiece.setLocation(event.getX() + xOffset, event.getY() + yOffset);
    }


    /**
     * @param event mouse event
     */
    protected final void computeHoverOffset(MouseEvent event) {
        final Point parentLocation = this.dragSource.getPosition(this.draggablePiece);

        this.xOffset = parentLocation.x - event.getX();
        this.yOffset = parentLocation.y - event.getY();
    }

}
