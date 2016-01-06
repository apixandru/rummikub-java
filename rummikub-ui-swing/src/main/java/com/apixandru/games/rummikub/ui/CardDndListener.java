/**
 *
 */
package com.apixandru.games.rummikub.ui;

import com.apixandru.games.rummikub.api.Player;

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
    private final Player<CardSlot> player;

    private CardSlot draggablePieceParent;
    private CardUi draggablePiece;

    private CardSlot dropTarget;
    private Color dropTargetOriginalColor;

    private int xOffset;
    private int yOffset;

    /**
     * @param dragSource
     * @param player
     */
    CardDndListener(final DragSource dragSource, final Player<CardSlot> player) {
        this.dragSource = dragSource;
        this.player = player;
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
        updateDropIndicator(e);
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
        final CardSlot component = getComponentOrInitialLocation(event);

        if (this.dropTarget != component) {
            resetBackground();

            this.dropTargetOriginalColor = UiUtil.getBackground(component);
            UiUtil.setBackground(component, this.hoverColor);
            this.dropTarget = component;
        }
    }

    /**
     */
    private void resetBackground() {
        UiUtil.setBackground(this.dropTarget, this.dropTargetOriginalColor);
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
        transferTo(getComponentOrInitialLocation(e));
        resetBackground();
        this.draggablePiece = null;
        this.dropTarget = null;
    }

    private void transferTo(final CardSlot destComponent) {
        final int toX = destComponent.x;
        final int toY = destComponent.y;
        final int fromX = this.draggablePieceParent.x;
        final int fromY = this.draggablePieceParent.y;

        switch (Transfer.of(this.draggablePieceParent, destComponent)) {
            case PLAYER_TO_BOARD:
                this.player.placeCardOnBoard(this.draggablePiece.card, toX, toY);
                break;
            case BOARD_TO_PLAYER:
                if (!this.player.canMoveCardOffBoard(this.draggablePiece.card)) {
                    transferTo(this.draggablePieceParent);
                    return;
                }
                this.player.takeCardFromBoard(this.draggablePiece.card, fromX, fromY, destComponent);
                break;
            case BOARD_TO_BOARD:
                this.player.moveCardOnBoard(this.draggablePiece.card, fromX, fromY, toX, toY);
                break;
            case PLAYER_TO_PLAYER:
                UiUtil.placeCard(this.draggablePiece, destComponent);
        }
    }

    /**
     * @param event
     * @return
     */
    private CardSlot getComponentOrInitialLocation(final MouseEvent event) {
        final JComponent component = getComponentAt(event);
        if (null == component || !canDrop(component)) {
            return this.draggablePieceParent;
        }
        return (CardSlot) component;
    }

    /**
     * @param component
     * @return
     */
    private static boolean canDrop(JComponent component) {
        return component instanceof CardSlot;
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

    private enum Transfer {
        BOARD_TO_PLAYER,
        PLAYER_TO_PLAYER,
        PLAYER_TO_BOARD,
        BOARD_TO_BOARD;

        static Transfer of(CardSlot from, CardSlot to) {
            final boolean toBoard = to.getParent() instanceof BoardUi;
            if (from.getParent() instanceof BoardUi) {
                if (toBoard) {
                    return BOARD_TO_BOARD;
                }
                return BOARD_TO_PLAYER;
            }
            if (toBoard) {
                return PLAYER_TO_BOARD;
            }
            return PLAYER_TO_PLAYER;
        }
    }

}
