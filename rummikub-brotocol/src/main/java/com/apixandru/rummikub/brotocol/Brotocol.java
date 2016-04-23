package com.apixandru.rummikub.brotocol;

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
    SERVER_NEW_TURN,

    CONNECT_CLIENT_START,
    CONNECT_CLIENT_LEAVE,

    CONNECT_SERVER_PLAYER_JOINED,
    CONNECT_SERVER_PLAYER_LEFT,
    CONNECT_SERVER_START

}