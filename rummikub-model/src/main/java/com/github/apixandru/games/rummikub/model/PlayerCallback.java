package com.github.apixandru.games.rummikub.model;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface PlayerCallback<H> {

    /**
     * @param card
     * @param hint
     */
    void cardReceived(Card card, H hint);

}