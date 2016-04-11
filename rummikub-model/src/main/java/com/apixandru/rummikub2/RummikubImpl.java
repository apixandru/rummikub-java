package com.apixandru.rummikub2;

import com.apixandru.rummikub.StateChangeListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class RummikubImpl<H> implements Rummikub<H> {

    private Map<String, StateChangeListener<H>> players = new HashMap<>();

    @Override
    public void register(final String playerName, final StateChangeListener<H> listener) throws RummikubException {
        if (players.containsKey(playerName)) {
            throw new RummikubException("Name already taken!");
        }
        players.put(playerName, listener);
    }

}
