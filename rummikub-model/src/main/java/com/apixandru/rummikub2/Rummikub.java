package com.apixandru.rummikub2;

import com.apixandru.rummikub.StateChangeListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface Rummikub {

    void register(String playerName, StateChangeListener stateChangeListener) throws RummikubException;

}
