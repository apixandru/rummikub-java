package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Player;

/**
 * @author Alexandru-Constantin Bledea
 * @since February 19, 2017
 */
interface EnhancedPlayer extends Player<Integer> {

    void receiveCard(Card card);

}
