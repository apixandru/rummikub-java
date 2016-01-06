package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface PlayerCallback<H> extends BoardCallback {

    /**
     * @param card
     * @param hint
     */
    void cardReceived(Card card, H hint);

}
