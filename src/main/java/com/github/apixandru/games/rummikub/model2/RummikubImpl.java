package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.CardPile;
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

    final BoardImpl board = new BoardImpl(undoManager);

    private final List<PlayerImpl> players = new ArrayList<>();

    private final CardPile cardPile = new CardPile();

    private final PlayerListener listener = new PlayerListenerImpl();

    PlayerImpl currentPlayer;

    {
        this.undoManager.reset(board);
    }

    private void endTurn() {
        boolean giveCard = true;
        if (this.undoManager.hasChanged(this.board)) {
            if (this.board.isValid()) {
                commit();
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

    /**
     *
     */
    private void commit() {
        this.board.lockPieces();
    }

    @Override
    public Player addPlayer(final String name) {
        final PlayerImpl player = new PlayerImpl(listener, getCards(14));
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

    public boolean placeCardOnBoard(Player player, Card card, int x, int y) {
        if (this.currentPlayer != player) {
            // only current player can place cards on board
            return false;
        }
        return this.board.placeCard(card, x, y);
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
                undoManager.addAction(new UndoPlayerToBoard(x, y));
            }
        }

        @Override
        public void moveCardOnBoard(final Player player, final Card card, final int fromX, final int fromY, final int toX, final int toY) {
            if (currentPlayer == player) {
                board.moveCard(fromX, toX, fromY, toY);
            }
        }
    }

}
