package com.apixandru.games.rummikub.ui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 19, 2015
 */
class PlayerUi extends JGridPanel {

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
        return getCardSlotStream()
                .filter(cardSlot -> 0 == cardSlot.getComponentCount())
                .findFirst()
                .get();
    }

    /**
     * @return
     */
    private Stream<CardSlot> getCardSlotStream() {
        return Arrays.stream(this.slots)
                .flatMap(Arrays::stream);
    }

    /**
     * @return
     */
    List<CardSlot> getAllSlots() {
        return getCardSlotStream().collect(Collectors.toList());
    }

}
