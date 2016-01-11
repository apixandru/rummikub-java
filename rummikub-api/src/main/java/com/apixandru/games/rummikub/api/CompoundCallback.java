package com.apixandru.games.rummikub.api;

/**
 * @param <H> hint
 * @author Alexandru-Constantin Bledea
 * @since January 11, 2016
 */
public interface CompoundCallback<H> extends PlayerCallback<H>, GameEventListener, BoardCallback {

}
