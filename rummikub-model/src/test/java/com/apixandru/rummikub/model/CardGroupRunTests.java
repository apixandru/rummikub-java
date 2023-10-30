package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Card;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.apixandru.rummikub.api.Color.BLACK;
import static com.apixandru.rummikub.api.Color.RED;
import static com.apixandru.rummikub.api.Rank.*;
import static com.apixandru.rummikub.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardGroupRunTests {

    private static CardGroup cardGroup(Card... cards) {
        return new CardGroup(Arrays.asList(cards));
    }

    @Test
    void test_pass_12_13_1() {
        CardGroup group = cardGroup(
                card(RED, TWELVE),
                card(RED, THIRTEEN),
                card(RED, ONE)
        );

        assertTrue(group.isValidRun());
    }

    @Test
    void test_pass_12_j_1() {
        CardGroup group = cardGroup(
                card(RED, TWELVE),
                JOKER_1,
                card(RED, ONE)
        );

        assertTrue(group.isValidRun());
    }

    @Test
    void test_pass_j_13_1() {
        CardGroup group = cardGroup(
                JOKER_2,
                card(RED, THIRTEEN),
                card(RED, ONE)
        );

        assertTrue(group.isValidRun());
    }

    @Test
    void test_fail_13_1_2() {
        CardGroup group = cardGroup(
                card(RED, THIRTEEN),
                card(RED, ONE),
                card(RED, TWO)
        );

        assertFalse(group.isValidRun());
    }

    @Test
    void test_pass_j_j_1() {
        CardGroup group = cardGroup(JOKER_1, JOKER_2, BLACK_ONE_1);

        assertTrue(group.isValidRun());
    }

    @Test
    void test_fail_j_j_2() {
        CardGroup group = cardGroup(JOKER_1, JOKER_2, card(BLACK, TWO));

        assertFalse(group.isValidRun());
    }

}
