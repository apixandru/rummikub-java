package com.apixandru.rummikub.api;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.apixandru.rummikub.api.GameOverReason.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOverReasonTest {

    @Test
    public void testReasons() {
        Set<GameOverReason> gameOverReasons = new HashSet<>(asList(values()));
        assertEquals(gameOverReasons, new HashSet<>(asList(PLAYER_QUIT, GAME_WON, NO_MORE_CARDS)));
    }

}
