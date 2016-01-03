package com.github.apixandru.games.rummikub.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class RummikubImpl implements Rummikub, BoardCallback {

    private final UndoManager undoManager = new UndoManager();

    final Board board;

    private final List<PlayerImpl> players = new ArrayList<>();

    private final CardPile cardPile = new CardPile();

    private final PlayerListener listener = new PlayerListenerImpl();

    PlayerImpl currentPlayer;

    /**
     *
     */
    RummikubImpl() {
        this.board = new Board(this);
        this.undoManager.reset(board);
    }

    private void endTurn() {
        boolean giveCard = true;
        if (this.undoManager.hasChanged(this.board)) {
            if (this.board.isValid() && !this.undoManager.justMovedCards(this.board)) {
                giveCard = false;
            } else {
                rollback();
            }
        }
        if (giveCard) {
            giveCard();
        }
        this.undoManager.reset(this.board);
        setNextPlayer();
    }

    /**
     *
     */
    private void giveCard() {
        this.currentPlayer.receiveCard(this.cardPile.nextCard());
    }

    /**
     *
     */
    private void setNextPlayer() {
        final int currentPlayerIndex = this.players.indexOf(this.currentPlayer);
        int nextPlayerIndex = currentPlayerIndex + 1;
        if (players.size() == nextPlayerIndex) {
            nextPlayerIndex = 0;
        }
        this.currentPlayer = this.players.get(nextPlayerIndex);
    }

    /**
     *
     */
    private void rollback() {
        undoManager.undo(currentPlayer, board);
    }

    @Override
    public <H> Player<H> addPlayer(final String name, final PlayerCallback<H> callback) {
        final PlayerImpl<H> player = new PlayerImpl<>(listener, callback);
        this.players.add(player);
        if (null == this.currentPlayer) {
            this.currentPlayer = player;
        }
        giveCards(14);
        return player;
    }

    /**
     * @param num
     * @return
     */
    private void giveCards(final int num) {
        for (int i = 0; i < num; i++) {
            giveCard();
        }
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        this.players.forEach(player -> player.onCardPlacedOnBoard(card, x, y));
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        this.players.forEach(player -> player.onCardRemovedFromBoard(card, x, y));
    }

    /**
     *
     */
    private class PlayerListenerImpl implements PlayerListener {

        @Override
        public void requestEndTurn(final Player player) {
            if (currentPlayer == player) {
                endTurn();
            }
        }

        @Override
        public void placeCardOnBoard(final Player player, final Card card, final int x, final int y) {
            if (currentPlayer == player && board.placeCard(card, x, y)) {
                currentPlayer.removeCard(card);
                undoManager.addAction(new UndoManager.UndoPlayerToBoard(x, y));
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void takeCardFromBoard(final Player player, final Card card, final int x, final int y, final Object hint) {
            if (currentPlayer == player && canMoveCardOffBoard(card)) {
                final Card cardFromBoard = board.removeCard(x, y);
                currentPlayer.receiveCard(cardFromBoard, hint);
                undoManager.addAction(new UndoManager.UndoBoardToPlayer(cardFromBoard, x, y));
            }
        }

        @Override
        public void moveCardOnBoard(final Player player, final Card card, final int fromX, final int fromY, final int toX, final int toY) {
            if (currentPlayer == player) {
                board.moveCard(fromX, fromY, toX, toY);
                undoManager.addAction(new UndoManager.UndoBoardToBoard(fromX, fromY, toX, toY));
            }
        }

        @Override
        public boolean canMoveCardOffBoard(final Card card) {
            return !undoManager.wasOnBoard(card);
        }

    }

}
