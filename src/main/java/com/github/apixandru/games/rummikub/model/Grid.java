package com.github.apixandru.games.rummikub.model;

import com.github.apixandru.games.rummikub.model.listeners.CardLocationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 22, 2015
 */
abstract class Grid {

    private final List<CardLocationListener> listeners = new ArrayList<>();

    final Card[][] cardsOnBoard;

    Grid(final int rows, final int cols) {
        this.cardsOnBoard = new Card[rows][cols];
    }


    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    public boolean placeCard(Card card, int x, int y) {
        if (inBounds(x, y) && isFree(x, y)) {
            cardsOnBoard[y][x] = card;
            informListeners(lstnr -> lstnr.onCardPlaced(card, x, y));
            return true;
        }
        return false;
    }

    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    public boolean removeCard(final Card card, int x, int y) {
        if (null == card || !inBounds(x, y) || cardsOnBoard[y][x] != card) {
            return false;
        }
        cardsOnBoard[y][x] = null;
        return true;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private boolean inBounds(final int x, final int y) {
        return y < NUM_ROWS && x < NUM_COLS;
    }

    /**
     * @param signal
     */
    private void informListeners(final Consumer<CardLocationListener> signal) {
        listeners.stream().forEach(signal);
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public boolean isFree(int x, int y) {
        return null == cardsOnBoard[y][x];
    }

    /**
     * @param listener
     */
    public void addListener(final CardLocationListener listener) {
        this.listeners.add(listener);
    }

}
