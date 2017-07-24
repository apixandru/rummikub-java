package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.PlayerCallback;
import com.apixandru.rummikub.swing.shared.CardSlot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 19, 2015
 */
class PlayerUi extends JGridPanel implements PlayerCallback<CardSlot> {

    PlayerUi() {
        super(3, 7);
    }

    private CardSlot orFirstFreeSlot(final CardSlot slot) {
        if (null != slot) {
            return slot;
        }
        return getCardSlotStream()
                .filter(cardSlot -> 0 == cardSlot.getComponentCount())
                .findFirst()
                .get();
    }

    private Stream<CardSlot> getCardSlotStream() {
        return Arrays.stream(this.slots)
                .flatMap(Arrays::stream);
    }

    List<CardSlot> getAllSlots() {
        return getCardSlotStream().collect(Collectors.toList());
    }

    @Override
    public void cardReceived(final Card card, final CardSlot hint) {
        UiUtil.placeCard(card, orFirstFreeSlot(hint));
    }

}
