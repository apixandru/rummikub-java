package com.apixandru.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface BoardListener {

    void onCardPlacedOnBoard(Card card, int x, int y);

    void onCardRemovedFromBoard(Card card, int x, int y, boolean reset);

}
