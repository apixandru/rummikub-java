package com.github.apixandru.games.rummikub.ui;

import java.util.Arrays;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 19, 2015
 */
public class PlayerUi extends JGridPanel {

    /**
     *
     */
    PlayerUi() {
        super(3, 7);
    }

    /**
     * @param slot
     * @return
     */
    public CardSlot orFirstFreeSlot(final CardSlot slot) {
        if (null != slot) {
            return slot;
        }
        return Arrays.stream(this.slots)
                .flatMap(Arrays::stream)
                .filter(cardSlot -> 0 == cardSlot.getComponentCount())
                .findFirst()
                .get();
    }

}
