package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.CardPile;
import com.github.apixandru.games.rummikub.model2.UndoManager.UndoBoardToBoard;
import com.github.apixandru.games.rummikub.model2.UndoManager.UndoBoardToPlayer;
import com.github.apixandru.games.rummikub.model2.UndoManager.UndoPlayerToBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class RummikubImpl implements Rummikub {

    private final UndoManager undoManager = new UndoManager();

    final Board board;

    private final List<PlayerImpl> players = new ArrayList<>();

    private final CardPile cardPile = new CardPile();

    private final PlayerListener listener = new PlayerListenerImpl();

    PlayerImpl currentPlayer;

    /**
     * @param callback
     */
    RummikubImpl(final BoardCallback callback) {
        this.board = new Board(callback);
        this.undoManager.reset(board);
    }

    private void endTurn() {
        boolean giveCard = true;
        if (this.undoManager.hasChanged(this.board)) {
            if (this.board.isValid()) {
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
    public Player addPlayer(final String name) {
        return addPlayer(name, null);
    }

    @Override
    public Player addPlayer(final String name, final RummikubCallback callback) {
        final PlayerImpl player = new PlayerImpl(listener, getCards(14), callback);
        this.players.add(player);
        if (null == this.currentPlayer) {
            this.currentPlayer = player;
        }
        return player;
    }

    /**
     * @param num
     * @return
     */
    private Collection<Card> getCards(final int num) {
        final List<Card> cards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            cards.add(cardPile.nextCard());
        }
        return cards;
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
                undoManager.addAction(new UndoPlayerToBoard(x, y));
            }
        }

        @Override
        public void takeCardFromBoard(final Player player, final Card card, final int x, final int y) {
            if (currentPlayer == player && !undoManager.wasOnBoard(card)) {
                final Card cardFromBoard = board.removeCard(x, y);
                currentPlayer.receiveCard(cardFromBoard);
                undoManager.addAction(new UndoBoardToPlayer(cardFromBoard, x, y));
            }
        }

        @Override
        public void moveCardOnBoard(final Player player, final Card card, final int fromX, final int fromY, final int toX, final int toY) {
            if (currentPlayer == player) {
                board.moveCard(fromX, fromY, toX, toY);
                undoManager.addAction(new UndoBoardToBoard(fromX, fromY, toX, toY));
            }
        }
    }

}
