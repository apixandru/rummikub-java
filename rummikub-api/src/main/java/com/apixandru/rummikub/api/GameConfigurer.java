package com.apixandru.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface GameConfigurer {

    void addGameEventListener(GameEventListener gameEventListener);

    void removeGameEventListener(GameEventListener gameEventListener);

    void addBoardListener(BoardListener boardListener);

    void removeBoardListener(BoardListener boardListener);

    Player<Integer> newPlayer(PlayerCallback<Integer> playerCallback);

    void removePlayer(Player<Integer> player);

}
