package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.api.PlayerCallback;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.apixandru.rummikub.api.GameOverReason.GAME_WON;
import static com.apixandru.rummikub.api.GameOverReason.NO_MORE_CARDS;
import static com.apixandru.rummikub.api.GameOverReason.PLAYER_QUIT;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class RummikubImpl implements Rummikub<Integer> {

    private static final Logger log = getLogger(RummikubImpl.class);

    final Board board = new BoardImpl();

    private final UndoManager undoManager = new UndoManager();
    private final List<GameEventListener> gameEventListeners = new ArrayList<>();
    private final Map<String, PlayerImpl> players = new LinkedHashMap<>();

    private final CardPile cardPile = new CardPile();

    private final PlayerListener listener = new PlayerListenerImpl();

    PlayerImpl currentPlayer;

    private boolean gameOver;

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
        if (currentPlayerHasMoreCardsInHand()) {
            gameOver(currentPlayer, GAME_WON);
        } else if (!gameOver) {
            setNextPlayer();
        }
    }

    private boolean currentPlayerHasMoreCardsInHand() {
        return currentPlayer.cards.isEmpty();
    }

    private void giveCard() {
        giveCard(this.currentPlayer);
    }

    private void giveCard(final PlayerImpl player) {
        if (!this.cardPile.hasMoreCards()) {
            final List<Card> cardsFromBoard = this.board.removeAllCards();
            if (cardsFromBoard.isEmpty()) {
                gameOver(player, NO_MORE_CARDS);
                return;
            }
            this.cardPile.setCards(cardsFromBoard);
        }
        player.receiveCard(this.cardPile.nextCard());
    }

    @Override
    public void setNextPlayer() {
        List<PlayerImpl> players = new ArrayList<>(this.players.values());
        if (players.isEmpty()) {
            throw new IllegalStateException("Cannot start game without any players!");
        }
        final int currentPlayerIndex = players.indexOf(this.currentPlayer);
        int nextPlayerIndex = currentPlayerIndex + 1;
        if (players.size() == nextPlayerIndex) {
            nextPlayerIndex = 0;
        }
        this.currentPlayer = players.get(nextPlayerIndex);
        signalNewTurn();
    }

    private void signalNewTurn() {
        final String currentPlayerName = getPlayerName(this.currentPlayer);
        this.gameEventListeners
                .forEach(listener -> listener.newTurn(currentPlayerName));
    }

    private void rollback() {
        undoManager.undo(currentPlayer, board);
    }

    @Override
    public Player<Integer> addPlayer(final String name, final PlayerCallback<Integer> callback) {
        final PlayerImpl player = new PlayerImpl(listener, callback);
        this.players.put(name, player);
        log.info("Player '{}' joined, Current players: {}", name, players.keySet());
        giveInitialCards(player);
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
        this.gameEventListeners.add(gameEventListener);
    }

    @Override
    public void removeGameEventListener(final GameEventListener gameEventListener) {
        gameEventListeners.remove(gameEventListener);
    }

    private String getPlayerName(Player<Integer> player) {
        return this.players.entrySet()
                .stream()
                .filter(e -> e.getValue() == player)
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
    }

    @Override
    public void removePlayer(Player<Integer> player) {
        String playerName = getPlayerName(player);
        if (null != this.players.remove(playerName)) {
            log.info("Player {} left, Remaining players: {}", playerName, players.keySet());
            gameOver(playerName, PLAYER_QUIT);
        }
    }

    private void gameOver(String playerName, GameOverReason reason) {
        if (!gameOver) {
            gameOver = true;
            List<GameEventListener> listeners = new ArrayList<>(this.gameEventListeners);
            this.gameEventListeners.clear();
            listeners.forEach(listener -> listener.gameOver(playerName, reason));
        }
    }

    private void gameOver(Player<Integer> player, GameOverReason reason) {
        String playerName = getPlayerName(player);
        gameOver(playerName, reason);
    }

    private void giveInitialCards(final PlayerImpl player) {
        for (int i = 0; i < 14; i++) {
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
            currentPlayer.removeCard(card);
            if (currentPlayer != player) {
                log.debug("It's not your turn!");
                player.receiveCard(card);
                return;
            }
            if (!board.placeCard(card, x, y)) {
                currentPlayer.receiveCard(card);
                return;
            }
            undoManager.trackPlayerToBoard(x, y);
        }

        @Override
        public void takeCardFromBoard(final PlayerImpl player, final Card card, final int x, final int y, final Integer hint) {
            if (card == null) {
                throw new IllegalArgumentException("Cannot have null card!"); // I wonder...
            }
            if (currentPlayer != player) {
                return;
            }
            Card cardOnBoard = board.getCardAt(x, y);
            if (canMoveCardOffBoard(cardOnBoard)) {
                final Card cardFromBoard = board.removeCard(x, y);
                currentPlayer.receiveCard(cardFromBoard, hint);
                undoManager.trackBoardToPlayer(cardFromBoard, x, y);
            }
        }

        @Override
        public void moveCardOnBoard(final PlayerImpl player, final int fromX, final int fromY, final int toX, final int toY) {
            if (currentPlayer == player) {
                board.moveCard(fromX, fromY, toX, toY);
                undoManager.trackBoardToBoard(fromX, fromY, toX, toY);
            }
        }

        private boolean canMoveCardOffBoard(final Card card) {
            return !undoManager.wasOnBoard(card);
        }

    }

}
