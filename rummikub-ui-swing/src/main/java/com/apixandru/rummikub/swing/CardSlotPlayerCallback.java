package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.PlayerCallback;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 21, 2016
 */
class CardSlotPlayerCallback implements PlayerCallback<Integer> {

    private final PlayerCallback<CardSlot> cardSlotPlayerCallback;
    private final List<CardSlot> cardSlots;

    CardSlotPlayerCallback(final PlayerCallback<CardSlot> cardSlotPlayerCallback, final List<CardSlot> cardSlots) {
        this.cardSlotPlayerCallback = cardSlotPlayerCallback;
        this.cardSlots = cardSlots;
    }

    @Override
    public String getPlayerName() {
        return cardSlotPlayerCallback.getPlayerName();
    }

    @Override
    public void cardReceived(Card card, Integer hint) {
        final CardSlot cardSlot = null == hint ? null : cardSlots.get(hint);
        cardSlotPlayerCallback.cardReceived(card, cardSlot);
    }

}
