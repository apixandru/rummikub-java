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
public abstract class Grid {

    private final List<CardLocationListener> listeners = new ArrayList<>();
    private final RummikubGame game;

    final Card[][] cards;

    Grid(final RummikubGame game, final int rows, final int cols) {
        this.cards = new Card[rows][cols];
        this.game = game;
    }

    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    public boolean placeCard(Card card, int x, int y) {
        if (!inBounds(x, y)) {
            return false;
        }
        // TODO maybe something more IntelliJ-ent
        if (isFree(x, y) || card == cards[y][x]) {
            game.removeCard(card);
            cards[y][x] = card;
            informListeners(lstnr -> lstnr.onCardPlaced(card, x, y));
            return true;
        }
        return false;
    }

    /**
     * @param card
     * @return
     */
    public boolean removeCard(final Card card) {
        for (int y = 0; y < cards.length; y++) {
            for (int x = 0; x < cards[y].length; x++) {
                if (card == cards[y][x]) {
                    cards[y][x] = null;
                    return true;
                }
            }
        }
        return false;
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
        return null == cards[y][x];
    }

    /**
     * @param listener
     */
    public void addListener(final CardLocationListener listener) {
        this.listeners.add(listener);
    }

}
