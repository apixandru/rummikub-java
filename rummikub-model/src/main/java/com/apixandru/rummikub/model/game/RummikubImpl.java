package com.apixandru.rummikub.model.game;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.api.PlayerCallback;

import java.util.ArrayList;
import java.util.List;

import static com.apixandru.rummikub.api.GameOverReason.GAME_WON;
import static com.apixandru.rummikub.api.GameOverReason.NO_MORE_CARDS;
import static com.apixandru.rummikub.api.GameOverReason.PLAYER_QUIT;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class RummikubImpl implements Rummikub<Integer> {

    final Board board = new Board();
    private final UndoManager undoManager = new UndoManager();
    private final List<GameEventListener> gameEventListeners = new ArrayList<>();
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
            this.gameEventListeners
                    .forEach(listener -> listener.gameOver(playerName, GAME_WON));
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
                this.gameEventListeners
                        .forEach(listener -> listener.gameOver(playerName, NO_MORE_CARDS));
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
        this.gameEventListeners
                .forEach(listener -> listener.newTurn(currentPlayerName));
    }

    private void rollback() {
        undoManager.undo(currentPlayer, board);
    }

    @Override
    public Player<Integer> addPlayer(final String name, final PlayerCallback<Integer> callback) {
        final PlayerImpl player = new PlayerImpl(name, listener, callback);
        this.players.add(player);
        if (null == this.currentPlayer) {
            setNextPlayer();
        }
        giveCards(player, 14);
        return player;
    }

    @Override
    public void addBoardListener(final BoardListener boardListener) {
        this.board.addBoardListener(boardListener);
    }

    @Override
    public void removeBoardListener(final BoardListener boardListener) {
        this.board.removeBoardListener(boardListener);
    }

    @Override
    public void addGameEventListener(final GameEventListener gameEventListener) {
        if (null != gameEventListener) {
            this.gameEventListeners.add(gameEventListener);
        }
    }

    @Override
    public void removeGameEventListener(final GameEventListener gameEventListener) {
        gameEventListeners
                .remove(gameEventListener);
    }

    @Override
    public void removePlayer(final Player<Integer> player) {
        if (this.players.remove(player)) {
            this.gameEventListeners
                    .forEach(listener -> listener.gameOver(player.getName(), PLAYER_QUIT));
        }
    }

    @Override
    public void removePlayer(String playerName) {
        PlayerImpl actualPlayer = players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Expecting to have a player named " + playerName));

        removePlayer(actualPlayer);
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