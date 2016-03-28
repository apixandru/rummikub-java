package com.apixandru.games.rummikub.api;

/**
 * @param <H> the hint type
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface PlayerCallback<H> {

    void cardReceived(Card card, H hint);

}
