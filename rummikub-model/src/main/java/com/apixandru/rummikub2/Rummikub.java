package com.apixandru.rummikub2;

import com.apixandru.rummikub.StateChangeListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface Rummikub<H> {

    void register(String playerName, StateChangeListener<H> stateChangeListener) throws RummikubException;

}
