package com.apixandru.rummikub.swing.shared;

import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.swing.util.AbstractDndListener;
import com.apixandru.rummikub.swing.util.DragSource;

import javax.swing.JComponent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 13, 2015
 */
public final class CardDndListener extends AbstractDndListener<CardUi, CardSlot> {

    private final Player<CardSlot> player;
    private final MoveHelper moveHelper;

    private final JGridPanel board;

    public CardDndListener(final DragSource<CardUi> dragSource, final JGridPanel board, final Player<CardSlot> player, final MoveHelper moveHelper) {
        super(CardUi.class, dragSource);
        this.player = player;
        this.board = board;
        this.moveHelper = moveHelper;
    }

    @Override
    protected boolean canDrag(final CardUi card) {
        return fromBoard((CardSlot) card.getParent()) && !moveHelper.canInteractWithBoard();
    }

    @Override
    protected void onDropped(final CardSlot destComponent) {
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
                this.player.moveCardOnBoard(fromX, fromY, toX, toY);
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

    private boolean fromBoard(final CardSlot slot) {
        return slot.getParent() == board;
    }

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
