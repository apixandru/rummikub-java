package com.apixandru.rummikub.swing;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 22, 2016
 */
final class CardSlotIndexConverter {

    private final List<CardSlot> cardSlots;

    public CardSlotIndexConverter(final List<CardSlot> cardSlots) {
        this.cardSlots = cardSlots;
    }

    Integer getIndex(final CardSlot cardSlot) {
        return null == cardSlot ? null : cardSlots.indexOf(cardSlot);
    }

    CardSlot getCardSlot(final Integer index) {
        return null == index ? null : cardSlots.get(index);
    }

}
