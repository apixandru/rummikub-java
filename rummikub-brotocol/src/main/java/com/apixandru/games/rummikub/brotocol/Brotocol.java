package com.apixandru.games.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public enum Brotocol {

    CLIENT_PLACE_CARD,
    CLIENT_MOVE_CARD,
    CLIENT_TAKE_CARD,
    CLIENT_END_TURN,

    SERVER_GAME_OVER,
    SERVER_RECEIVED_CARD,
    SERVER_CARD_PLACED,
    SERVER_CARD_REMOVED,
    SERVER_NEW_TURN;

}
