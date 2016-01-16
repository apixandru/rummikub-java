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

    private final Color colorAccept;
    private final Color colorReject;

    private final DragSource<C> dragSource;
    private final Class<C> componentClass;

    protected P draggablePieceParent;
    protected C draggablePiece;

    private int xOffset;
    private int yOffset;

    private P dropTarget;
    private Color dropTargetOriginalColor;

    /**
     * @param componentClass draggable type class
     * @param dragSource     the drag source
     */
    protected AbstractDndListener(final Class<C> componentClass, final DragSource<C> dragSource) {
        this(componentClass, dragSource, new Color(0xccffcc), new Color(0xffcccf));
    }

    /**
     * @param componentClass draggable type class
     * @param dragSource     the drag source
     * @param acceptColor    the accept drop indicator color
     * @param rejectColor    the accept drop indicator color
     */
    protected AbstractDndListener(final Class<C> componentClass,
                                  final DragSource<C> dragSource,
                                  final Color acceptColor,
                                  final Color rejectColor) {
        this.dragSource = dragSource;
        this.componentClass = componentClass;
        this.colorAccept = acceptColor;
        this.colorReject = rejectColor;
    }

    /**
     *
     */
    private void resetBackground() {
        SwingUtil.setBackground(this.dropTarget, this.dropTargetOriginalColor);
        this.dropTarget = null;
    }

    /**
     * @param event mouse event
     * @return component
     */
    private JComponent getComponentAt(final MouseEvent event) {
        return this.dragSource.getComponentAt(event.getX(), event.getY());
    }

    @Override
    public final void mouseDragged(final MouseEvent e) {
        if (null == this.draggablePiece) {
            return;
        }
        updateMovingPieceLocation(e);
        updateDropIndicator(e);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        if (this.draggablePiece == null) {
            return;
        }
        this.dragSource.endDrag(this.draggablePiece);
        onDropped(getComponentOrInitialLocation(e));
        resetBackground();
        this.draggablePiece = null;
    }

    /**
     * @param target target
     */
    protected abstract void onDropped(final P target);

    /**
     * @param event mouse event
     * @return draggable
     */
    @SuppressWarnings("unchecked")
    private C getDraggable(final MouseEvent event) {
        Component c = getComponentAt(event);
        while (null != c && !(componentClass.isInstance(c))) {
            c = c.getParent();
        }
        return (C) c;
    }

    /**
     * @param draggable the draggable piece
     * @return can drag
     */
    protected abstract boolean canDrag(C draggable);

    /**
     * @param target the drop candidate
     * @return can drop
     */
    protected abstract boolean canDrop(JComponent target);

    /**
     * @param event mouse event
     */
    private void updateDropIndicator(final MouseEvent event) {
        final P component = getComponentOrInitialLocation(event);

        if (this.dropTarget != component) {
            resetBackground();

            this.dropTargetOriginalColor = SwingUtil.getBackground(component);
            SwingUtil.setBackground(component, getDropIndicatorColor(component));
            this.dropTarget = component;
        }
    }

    /**
     * @param dropTarget the drop target
     * @return colorReject if the target is the same as the initial location, accept otherwise
     */
    private Color getDropIndicatorColor(final P dropTarget) {
        return dropTarget == this.draggablePieceParent ? this.colorReject : this.colorAccept;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void mousePressed(final MouseEvent e) {
        final C card = getDraggable(e);
        if (null == card) {
            return;
        }
        if (canDrag(card)) {
            return;
        }
        this.draggablePiece = card;

        this.draggablePieceParent = (P) card.getParent();

        computeHoverOffset(e);

        this.dragSource.beginDrag(this.draggablePiece);

        updateMovingPieceLocation(e);
        updateDropIndicator(e);
    }

    /**
     * @param event mouse event
     * @return the component or initial location
     */
    @SuppressWarnings("unchecked")
    private P getComponentOrInitialLocation(final MouseEvent event) {
        final JComponent component = getComponentAt(event);
        if (null == component || !canDrop(component)) {
            return this.draggablePieceParent;
        }
        return (P) component;
    }

    /**
     * @param event mouse event
     */
    private void updateMovingPieceLocation(final MouseEvent event) {
        this.draggablePiece.setLocation(event.getX() + xOffset, event.getY() + yOffset);
    }

    /**
     * @param event mouse event
     */
    private void computeHoverOffset(MouseEvent event) {
        final Point parentLocation = this.dragSource.getPosition(this.draggablePiece);
        this.xOffset = parentLocation.x - event.getX();
        this.yOffset = parentLocation.y - event.getY();
    }

}
