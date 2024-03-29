package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.apixandru.rummikub.api.Constants.NUM_COLS;
import static com.apixandru.rummikub.api.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class BoardImpl implements Board {

    private static final Logger log = LoggerFactory.getLogger(BoardImpl.class);

    private final Card[][] cards = new Card[NUM_ROWS][NUM_COLS];

    private final List<BoardListener> boardListeners = new ArrayList<>();

    private static boolean inBounds(final int x, final int y) {
        return y < NUM_ROWS && x < NUM_COLS;
    }

    boolean isFree(int x, int y) {
        return null == getCardAt(x, y);
    }

    @Override
    public Card getCardAt(int x, int y) {
        return cards[y][x];
    }

    @Override
    public boolean placeCard(Card card, int x, int y) {
        if (card == null) {
            throw new IllegalArgumentException("No card specified!");
        }
        if (!inBounds(x, y)) {
            log.debug("Cannot place {} outside of the bounds. ({}/{})", card, x, y);
            return false;
        }
        if (!isFree(x, y)) {
            log.debug("Cannot place {} at ({}/{}). That slot is occupied by {}", card, x, y, cards[y][x]);
            return false;
        }
        cards[y][x] = card;
        log.debug("Placed {} at {}/{}", card, x, y);
        boardListeners.forEach(boardListener -> boardListener.onCardPlacedOnBoard(card, x, y));
        return true;
    }

    private Card removeCard(final int x, final int y, final boolean unlock) {
        final Card card = getCardAt(x, y);
        log.debug("Removed {} from {}/{}", card, x, y);
        cards[y][x] = null;
        boardListeners.forEach(boardListener -> boardListener.onCardRemovedFromBoard(card, x, y, unlock));
        return card;
    }

    @Override
    public Card removeCard(final int x, final int y) {
        return removeCard(x, y, false);
    }

    @Override
    public void moveCard(final int fromX, final int fromY, final int toX, final int toY) {
        final Card card = removeCard(fromX, fromY);
        if (!placeCard(card, toX, toY)) {
            final boolean inBounds = inBounds(toX, toY);
            final boolean free = isFree(toX, toY);
            log.error("Cannot place {} on board. inBounds={}, free={}, cards[{}][{}]={}", card, inBounds, free, toX, toY, cards[toY][toX]);
            placeCard(card, fromX, fromY);
        }
    }

    @Override
    public boolean isValid() {
        return streamGroups().allMatch(CardGroup::isValid);
    }

    Stream<CardGroup> streamGroups() {
        return Arrays.stream(cards)
                .map(Util::splitNonEmptyGroups)
                .flatMap(Collection::stream)
                .map(CardGroup::new);
    }

    @Override
    public List<Card> removeAllCards() {
        final List<Card> cards = new ArrayList<>();
        for (int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                if (!isFree(x, y)) {
                    cards.add(removeCard(x, y, true));
                }
            }
        }
        return cards;
    }

    @Override
    public Card[][] getCards() {
        return cards;
    }

    @Override
    public void addBoardListener(final BoardListener boardListener) {
        this.boardListeners.add(boardListener);
    }

    @Override
    public void removeBoardListener(final BoardListener boardListener) {
        this.boardListeners.remove(boardListener);
    }

}
