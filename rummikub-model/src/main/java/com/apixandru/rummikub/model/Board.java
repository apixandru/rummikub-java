package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since February 19, 2017
 */
interface Board {

    Card removeCard(int x, int y);

    void moveCard(int toX, int toY, int fromX, int fromY);

    boolean placeCard(Card card, int x, int y);

    Card[][] getCards();

    boolean isValid();

    List<Card> removeAllCards();

    void addBoardListener(BoardListener boardListener);

    void removeBoardListener(BoardListener boardListener);

}
