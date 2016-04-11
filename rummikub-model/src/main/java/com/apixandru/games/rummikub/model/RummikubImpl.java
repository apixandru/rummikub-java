package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.apixandru.games.rummikub.api.GameOverReason.GAME_WON;
import static com.apixandru.games.rummikub.api.GameOverReason.NO_MORE_CARDS;
import static com.apixandru.games.rummikub.api.GameOverReason.PLAYER_QUIT;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class RummikubImpl implements Rummikub<Integer> {

    final Board board = new Board();
    private final UndoManager undoManager = new UndoManager();
    private final Map<PlayerImpl, GameEventListener> gameEventListeners = new LinkedHashMap<>();
    private final List<PlayerImpl> players = new ArrayList<>();

    private final CardPile cardPile = new CardPile();

    private final PlayerListener listener = new PlayerListenerImpl();

    PlayerImpl currentPlayer;

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
            this.gameEventListeners.forEach(
                    (player, listener) -> listener.gameOver(playerName, GAME_WON)
            );
            return;
        }
        setNextPlayer();
    }

    private void giveCard() {
        giveCard(this.currentPlayer);
    }

    private void giveCard(final PlayerImpl player) {
        if (!this.cardPile.hasMoreCards()) {
            final List<Card> cardsFromBoard = this.board.removeAllCards();
            if (cardsFromBoard.isEmpty()) {
                String playerName = player.getName();
                this.gameEventListeners.forEach(
                        (player1, listener) -> listener.gameOver(playerName, NO_MORE_CARDS)
                );
                return;
            }
            this.cardPile.setCards(cardsFromBoard);
        }
        player.receiveCard(this.cardPile.nextCard());
    }

    private void setNextPlayer() {
        final int currentPlayerIndex = this.players.indexOf(this.currentPlayer);
        int nextPlayerIndex = currentPlayerIndex + 1;
        if (players.size() == nextPlayerIndex) {
            nextPlayerIndex = 0;
        }
        this.currentPlayer = this.players.get(nextPlayerIndex);
        signalNewTurn();
    }

    private void signalNewTurn() {
        final String currentPlayerName = this.currentPlayer.getName();
        this.gameEventListeners.forEach(
                (player, listener) -> listener.newTurn(currentPlayerName, player == this.currentPlayer)
        );
    }

    private void rollback() {
        undoManager.undo(currentPlayer, board);
    }

    @Override
    public Player<Integer> addPlayer(final String name, final CompoundCallback<Integer> callback) {
        final PlayerImpl player = new PlayerImpl(name, listener, callback);
        this.board.addBoardListener(callback);
        if (null != callback) {
            this.gameEventListeners.put(player, callback);
        }
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
            this.gameEventListeners.remove(player);
            final String playerName = player.getName();
            this.gameEventListeners.forEach(
                    (player1, listener) -> listener.gameOver(playerName, PLAYER_QUIT)
            );
        }
    }

    private void giveCards(final PlayerImpl player, final int num) {
        for (int i = 0; i < num; i++) {
            giveCard(player);
        }
    }

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

        private boolean canMoveCardOffBoard(final Card card) {
            return !undoManager.wasOnBoard(card);
        }

    }

}
