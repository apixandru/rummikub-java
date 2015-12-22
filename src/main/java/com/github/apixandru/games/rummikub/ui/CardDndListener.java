/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 13, 2015
 */
final class CardDndListener extends MouseAdapter {

    private final Color hoverColor = Color.PINK;

    private final DragSource dragSource;

    private CardSlot draggablePieceParent;
    private CardUi draggablePiece;

    private Container dropTarget;
    private Color dropTargetOriginalColor;


    private int xOffset;
    private int yOffset;


    /**
     * @param dragSource
     */
    CardDndListener(final DragSource dragSource) {
        this.dragSource = dragSource;
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(final MouseEvent e) {
        final CardUi card = getCard(e);
        if (null == card) {
            return;
        }
        this.draggablePiece = card;

        this.draggablePieceParent = (CardSlot) card.getParent();

        computeHoverOffset(e);

        this.dragSource.beginDrag(this.draggablePiece);

        updateMovingPieceLocation(e);
    }

    /**
     * @param e
     */
    private void computeHoverOffset(MouseEvent e) {
        final Point parentLocation = this.dragSource.getPosition(this.draggablePiece);

        this.xOffset = parentLocation.x - e.getX();
        this.yOffset = parentLocation.y - e.getY();
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(final MouseEvent e) {
        if (this.draggablePiece == null) {
            return;
        }
        updateMovingPieceLocation(e);
        updateDropIndicator(e);
    }

    /**
     * @param event
     */
    private void updateDropIndicator(final MouseEvent event) {
        final Container component = getComponentOrInitialLocation(event);

        if (this.dropTarget != component) {
            UiUtil.setBackground(this.dropTarget, this.dropTargetOriginalColor);

            this.dropTargetOriginalColor = UiUtil.getBackground(component);
            UiUtil.setBackground(component, this.hoverColor);
            this.dropTarget = component;
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(final MouseEvent e) {
        if (this.draggablePiece == null) {
            return;
        }
        this.dragSource.endDrag(this.draggablePiece);

        final Container destComponent = getComponentOrInitialLocation(e);
        getGrid(destComponent).placeCard(this.draggablePiece, (CardSlot) destComponent);
        this.draggablePiece = null;
    }

    /**
     * @param container
     * @return
     */
    private JGridPanel getGrid(final Container container) {
        Component component = container;
        while (component != null && !(component instanceof JGridPanel)) {
            component = component.getParent();
        }
        return (JGridPanel) component;
    }

    /**
     * @param event
     * @return
     */
    private Container getComponentOrInitialLocation(final MouseEvent event) {
        final JComponent component = getComponentAt(event);
        if (null == component || !canDrop(component)) {
            return this.draggablePieceParent;
        }
        return component;
    }

    /**
     * @param component
     * @return
     */
    private static boolean canDrop(JComponent component) {
        // TODO fix this
        if (component instanceof CardSlot) {
            return true;
        }
        return false;
    }

    /**
     * @param event
     * @return
     */
    private CardUi getCard(final MouseEvent event) {
        Component c = getComponentAt(event);
        while (null != c && !(c instanceof CardUi)) {
            c = c.getParent();
        }
        return (CardUi) c;
    }

    /**
     * @param event
     * @return
     */
    private JComponent getComponentAt(final MouseEvent event) {
        return this.dragSource.getComponentAt(event.getX(), event.getY());
    }

    /**
     * @param event
     */
    private void updateMovingPieceLocation(final MouseEvent event) {
        this.draggablePiece.setLocation(event.getX() + xOffset, event.getY() + yOffset);
    }

}
