package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

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

    private boolean gameOver;

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
        gameOverOrSetNextPlayer();
    }

    private void gameOverOrSetNextPlayer() {
        if (currentPlayer.cards.isEmpty()) {
            this.players.forEach(player -> player.gameOver(currentPlayer.name, false, player == this.currentPlayer));
            this.gameOver = true;
            return;
        }
        setNextPlayer();
    }

    /**
     *
     */
    private void giveCard() {
        giveCard(this.currentPlayer);
    }

    /**
     * @param player
     */
    private void giveCard(final PlayerImpl<?> player) {
        player.receiveCard(this.cardPile.nextCard());
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
        signalNewTurn();
    }

    /**
     *
     */
    private void signalNewTurn() {
        players.forEach(player -> player.newTurn(player == this.currentPlayer));
    }

    /**
     *
     */
    private void rollback() {
        undoManager.undo(currentPlayer, board);
    }

    @Override
    public <H> Player<H> addPlayer(final String name, final PlayerCallback<H> callback) {
        final PlayerImpl<H> player = new PlayerImpl<>(name, listener, callback);
        this.players.add(player);
        if (null == this.currentPlayer) {
            setNextPlayer();
        }
        giveCards(player, 14);
        return player;
    }

    /**
     * @param player
     * @param num
     */
    private void giveCards(final PlayerImpl<?> player, final int num) {
        for (int i = 0; i < num; i++) {
            giveCard(player);
        }
    }

    @Override
    public List<Card> getCards() {
        return new ArrayList<>(this.cardPile.cards);
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

        /**
         * @param card
         * @return
         */
        private boolean canMoveCardOffBoard(final Card card) {
            return !undoManager.wasOnBoard(card);
        }

    }

}
