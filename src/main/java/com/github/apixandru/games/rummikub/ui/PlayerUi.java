package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Player;
import com.github.apixandru.games.rummikub.model.listeners.CardLocationListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 19, 2015
 */
public class PlayerUi extends JGridPanel {

    /**
     * @param player
     */
    private PlayerUi(Player player) {
        super(player, 3, 7);
    }

    /**
     * @param player
     * @return
     */
    static PlayerUi createPlayerUi(Player player) {
        final PlayerUi playerUi = new PlayerUi(player);
        player.addListener(playerUi.new PlayerUiCardListener());
        return playerUi;
    }

    private class PlayerUiCardListener implements CardLocationListener {

        @Override
        public void onCardPlaced(final Card card, final int x, final int y) {
            UiUtil.placeCard(new CardUi(card), slots[y][x]);
        }
    }

}
