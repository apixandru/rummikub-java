package com.github.apixandru.games.rummikub.api;

import com.github.apixandru.games.rummikub.api.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.api.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class Board {

    final Card[][] cards;
    private final Optional<BoardCallback> callback;

    /**
     * @param callback
     */
    Board(final BoardCallback callback) {
        this.callback = Optional.ofNullable(callback);
        this.cards = new Card[NUM_ROWS][NUM_COLS];
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
     * @param x
     * @param y
     * @return
     */
    boolean isFree(int x, int y) {
        return null == cards[y][x];
    }


    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    boolean placeCard(Card card, int x, int y) {
        if (!inBounds(x, y)) {
            return false;
        }
        if (!isFree(x, y) && card != cards[y][x]) {
            return false;
        }
        cards[y][x] = card;
        this.callback.ifPresent(callback -> callback.onCardPlacedOnBoard(card, x, y));
        return true;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    Card removeCard(final int x, final int y) {
        final Card card = cards[y][x];
        this.callback.ifPresent(callback -> callback.onCardRemovedFromBoard(card, x, y));
        cards[y][x] = null;
        return card;
    }

    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    void moveCard(final int fromX, final int fromY, final int toX, final int toY) {
        final Card card = removeCard(fromX, fromY);
        placeCard(card, toX, toY);
    }

    /**
     * @return
     */
    public boolean isValid() {
        return streamGroups().allMatch(CardGroup::isValid);
    }

    /**
     * @return
     */
    Stream<CardGroup> streamGroups() {
        return Arrays.stream(cards)
                .map(Util::splitNonEmptyGroups)
                .flatMap(List::stream)
                .map(CardGroup::new);
    }

}
