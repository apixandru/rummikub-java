package com.apixandru.games.rummikub.api;

/**
 * @param <H> the hint type
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface PlayerCallback<H> {

    /**
     * @param card the card
     * @param hint the hint for the client
     */
    void cardReceived(Card card, H hint);

}
