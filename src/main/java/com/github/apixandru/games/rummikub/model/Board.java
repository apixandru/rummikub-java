/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import com.github.apixandru.games.rummikub.model.listeners.BoardListener;
import com.github.apixandru.games.rummikub.model.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 21, 2015
 */
public final class Board {

    private static final int NUM_ROWS = 7;
    private static final int NUM_COLS = 20;

    private final Card[][] cardsOnBoard = new Card[NUM_ROWS][NUM_COLS];

    private final List<BoardListener> listeners = new ArrayList<>();

    public boolean placeCard(Card card, int x, int y) {
        if (inBounds(x, y) && isFree(x, y)) {
            cardsOnBoard[y][x] = card;
            return true;
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

    public List<CardGroup> getGroups() {
        return streamGroups(cardsOnBoard).collect(Collectors.toList());
    }

    public boolean isValid() {
        return streamGroups(cardsOnBoard).allMatch(CardGroup::isValid);
    }

    private Stream<CardGroup> streamGroups(Card[][] cardsOnBoard) {
        return Arrays.stream(cardsOnBoard)
                .map(Util::splitNonEmptyGroups)
                .flatMap(List::stream)
                .map(CardGroup::new);
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
    public void addListener(final BoardListener listener) {
        this.listeners.add(listener);
    }

}
