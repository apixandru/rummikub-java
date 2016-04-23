package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.StateChangeListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
interface Rummikub {

    void register(String playerName, StateChangeListener stateChangeListener) throws RummikubException;

}
