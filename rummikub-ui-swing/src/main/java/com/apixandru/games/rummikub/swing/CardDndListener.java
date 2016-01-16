/**
 *
 */
package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.utils.swing.AbstractDndListener;
import com.apixandru.utils.swing.DragSource;
import com.apixandru.utils.swing.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 13, 2015
 */
final class CardDndListener extends AbstractDndListener<CardUi, CardSlot> {

    private final Color hoverColor = Color.PINK;

    private final Player<CardSlot> player;
    private final MoveHelper moveHelper;

    private final JGridPanel board;

    private CardSlot dropTarget;
    private Color dropTargetOriginalColor;

    /**
     * @param dragSource
     * @param board
     * @param player
     * @param moveHelper
     */
    CardDndListener(final DragSource<CardUi> dragSource, final JGridPanel board, final Player<CardSlot> player, final MoveHelper moveHelper) {
        super(CardUi.class, dragSource);
        this.player = player;
        this.board = board;
        this.moveHelper = moveHelper;
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(final MouseEvent e) {
        final CardUi card = getDraggable(e);
        if (null == card) {
            return;
        }
        if (canDrag(card)) {
            return;
        }
        this.draggablePiece = card;

        this.draggablePieceParent = (CardSlot) card.getParent();

        computeHoverOffset(e);

        this.dragSource.beginDrag(this.draggablePiece);

        updateMovingPieceLocation(e);
        updateDropIndicator(e);
    }

    @Override
    protected boolean canDrag(final CardUi card) {
        return fromBoard((CardSlot) card.getParent()) && !moveHelper.canInteractWithBoard();
    }

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

            this.dropTargetOriginalColor = SwingUtil.getBackground(component);
            SwingUtil.setBackground(component, this.hoverColor);
            this.dropTarget = component;
        }
    }

    /**
     */
    private void resetBackground() {
        SwingUtil.setBackground(this.dropTarget, this.dropTargetOriginalColor);
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

    /**
     * @param destComponent
     */
    private void transferTo(final CardSlot destComponent) {
        final int toX = destComponent.x;
        final int toY = destComponent.y;
        final int fromX = this.draggablePieceParent.x;
        final int fromY = this.draggablePieceParent.y;

        switch (transferOf(this.draggablePieceParent, destComponent)) {
            case PLAYER_TO_BOARD:
                this.player.placeCardOnBoard(this.draggablePiece.card, toX, toY);
                break;
            case BOARD_TO_PLAYER:
                this.player.takeCardFromBoard(this.draggablePiece.card, fromX, fromY, destComponent);
                break;
            case BOARD_TO_BOARD:
                if (!this.moveHelper.canInteractWithBoard()) {
                    UiUtil.placeCard(this.draggablePiece, destComponent);
                    return;
                }
                this.player.moveCardOnBoard(this.draggablePiece.card, fromX, fromY, toX, toY);
                break;
            default:
                UiUtil.placeCard(this.draggablePiece, destComponent);
        }
    }

    @Override
    protected boolean canDrop(JComponent component) {
        if (!(component instanceof CardSlot)) {
            return false;
        }
        final Transfer type = transferOf(this.draggablePieceParent, (CardSlot) component);
        if (type == Transfer.PLAYER_TO_PLAYER || type == Transfer.NONE) {
            return true;
        }
        if (!this.moveHelper.canInteractWithBoard()) {
            return false;
        }
        return type != Transfer.BOARD_TO_PLAYER || this.moveHelper.canTakeCardFromBoard(this.draggablePiece.card);
    }

    /**
     * @param slot
     * @return
     */
    private boolean fromBoard(final CardSlot slot) {
        return slot.getParent() == board;
    }

    /**
     * @param from
     * @param to
     * @return
     */
    private Transfer transferOf(final CardSlot from, final CardSlot to) {
        if (to == from) {
            return Transfer.NONE;
        }
        final boolean toBoard = fromBoard(to);
        if (fromBoard(from)) {
            if (toBoard) {
                return Transfer.BOARD_TO_BOARD;
            }
            return Transfer.BOARD_TO_PLAYER;
        }
        if (toBoard) {
            return Transfer.PLAYER_TO_BOARD;
        }
        return Transfer.PLAYER_TO_PLAYER;
    }

    private enum Transfer {
        BOARD_TO_PLAYER,
        PLAYER_TO_PLAYER,
        PLAYER_TO_BOARD,
        BOARD_TO_BOARD,
        NONE
    }

}
