package com.apixandru.games.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public interface Brotocol {

    int CLIENT_PLACE_CARD = 0;
    int CLIENT_MOVE_CARD = 1;
    int CLIENT_TAKE_CARD = 2;
    int CLIENT_END_TURN = 3;

    int SERVER_GAME_OVER = 4;
    int SERVER_RECEIVED_CARD = 5;
    int SERVER_CARD_PLACED = 6;
    int SERVER_CARD_REMOVED = 7;
    int SERVER_NEW_TURN = 8;

}
