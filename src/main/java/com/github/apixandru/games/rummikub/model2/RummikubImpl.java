package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
class RummikubImpl implements Rummikub {

    private final Board board = new BoardImpl();

    private final List<Player> players = new ArrayList<>();
    private Player currentPlayer;


    @Override
    public void endTurn() {
        if (this.board.isValid()) {
            commit();
        } else {
            rollback();
        }
        setNextPlayer();
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

    }

    /**
     *
     */
    private void commit() {
        this.board.lockPieces();
    }

    @Override
    public Player currentPlayer() {
        return currentPlayer;
    }

    @Override
    public Player addPlayer(final String name) {
        final PlayerImpl player = new PlayerImpl();
        this.players.add(player);
        if (null == this.currentPlayer) {
            this.currentPlayer = player;
        }
        return player;
    }

    @Override
    public boolean placeCardOnBoard(Player player, Card card, int x, int y) {
        if (this.currentPlayer != player) {
            // only current player can place cards on board
            return false;
        }
        return board.placeCard(card, x, y);
    }

}
