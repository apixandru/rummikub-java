package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class RummikubImpl implements Rummikub<Integer> {

    private final UndoManager undoManager = new UndoManager();

    final Board board = new Board();

    private final List<PlayerImpl> players = new ArrayList<>();

    private final CardPile cardPile = new CardPile();

    private final PlayerListener listener = new PlayerListenerImpl();

    PlayerImpl currentPlayer;

    private boolean gameOver;

    /**
     *
     */
    RummikubImpl() {
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
            final String playerName = currentPlayer.getName();
            this.players.forEach(
                    player -> player.callback.ifPresent(
                            callback -> callback.gameOver(playerName, false, player == this.currentPlayer)
                    )
            );
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
    private void giveCard(final PlayerImpl player) {
        if (!this.cardPile.hasMoreCards()) {
            this.cardPile.setCards(this.board.removeAllCards());
        }
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
        this.players.forEach(
                player -> player.callback.ifPresent(
                        callback -> callback.newTurn(player == this.currentPlayer)
                )
        );
    }

    /**
     *
     */
    private void rollback() {
        undoManager.undo(currentPlayer, board);
    }

    @Override
    public Player<Integer> addPlayer(final String name, final PlayerCallback<Integer> callback) {
        final PlayerImpl player = new PlayerImpl(name, listener, callback);
        this.board.addBoardListener(callback);
        this.players.add(player);
        if (null == this.currentPlayer) {
            setNextPlayer();
        }
        giveCards(player, 14);
        return player;
    }

    @Override
    public void removePlayer(final Player<Integer> player) {
        if (this.players.remove(player)) {
            final String playerName = player.getName();
            this.players.forEach(
                    player1 -> player1.callback.ifPresent(
                            callback -> callback.gameOver(playerName, true, false)
                    )
            );
            this.gameOver = true;
        }
    }

    /**
     * @param player
     * @param num
     */
    private void giveCards(final PlayerImpl player, final int num) {
        for (int i = 0; i < num; i++) {
            giveCard(player);
        }
    }

    @Override
    public List<Card> getCards() {
        return new ArrayList<>(this.cardPile.cards);
    }

    /**
     *
     */
    private class PlayerListenerImpl implements PlayerListener {

        @Override
        public void requestEndTurn(final PlayerImpl player) {
            if (currentPlayer == player) {
                endTurn();
            }
        }

        @Override
        public void placeCardOnBoard(final PlayerImpl player, final Card card, final int x, final int y) {
            if (currentPlayer == player && board.placeCard(card, x, y)) {
                currentPlayer.removeCard(card);
                undoManager.addAction(new UndoManager.UndoPlayerToBoard(x, y));
            }
        }

        @Override
        public void takeCardFromBoard(final PlayerImpl player, final Card card, final int x, final int y, final Integer hint) {
            if (currentPlayer == player && canMoveCardOffBoard(card)) {
                final Card cardFromBoard = board.removeCard(x, y);
                currentPlayer.receiveCard(cardFromBoard, hint);
                undoManager.addAction(new UndoManager.UndoBoardToPlayer(cardFromBoard, x, y));
            }
        }

        @Override
        public void moveCardOnBoard(final PlayerImpl player, final Card card, final int fromX, final int fromY, final int toX, final int toY) {
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
