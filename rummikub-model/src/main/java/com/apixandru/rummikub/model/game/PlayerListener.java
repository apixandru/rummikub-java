package com.apixandru.rummikub.model.game;

import com.apixandru.rummikub.api.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
interface PlayerListener {

    void requestEndTurn(PlayerImpl player);

    void placeCardOnBoard(PlayerImpl player, Card card, int x, int y);

    void takeCardFromBoard(PlayerImpl player, Card card, int x, int y, final Integer hint);

    void moveCardOnBoard(PlayerImpl player, Card card, int fromX, int fromY, int toX, int toY);

}
