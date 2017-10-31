package com.techtest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardTest {
    private PokerHandsComparator main;

    @Before
    public void setUp() {
        main = new PokerHandsComparator();
    }

    @Test
    public void testCardsWithSameValuesDiffSuits() {
        Card cardOne = main.buildCard("2", Card.Suit.C);
        Card cardTwo = main.buildCard("2", Card.Suit.D);

        assertNotEquals(cardOne, cardTwo);
        assertEquals(0, cardOne.compareTo(cardTwo));
    }

    @Test
    public void testCardsWithDiffValues() {
        Card cardOne = main.buildCard("A", Card.Suit.C);
        Card cardTwo = main.buildCard("2", Card.Suit.D);

        assertNotEquals(cardOne, cardTwo);
        assertEquals(1, cardOne.compareTo(cardTwo));
    }

    @Test
    public void testEqualCards() {
        Card cardOne = main.buildCard("2", Card.Suit.D);
        Card cardTwo = main.buildCard("2", Card.Suit.D);

        assertEquals(cardOne, cardTwo);
        assertEquals(0, cardOne.compareTo(cardTwo));
    }

    @Test
    public void testActualNumber() {
        Card AC = main.buildCard("A", Card.Suit.C);
        Card KC = main.buildCard("K", Card.Suit.C);
        Card QC = main.buildCard("Q", Card.Suit.C);
        Card JC = main.buildCard("J", Card.Suit.C);
        Card TC = main.buildCard("T", Card.Suit.C);
        Card cardSix = main.buildCard("9", Card.Suit.C);

        assertEquals(14, AC.getActualNumber());
        assertEquals(13, KC.getActualNumber());
        assertEquals(12, QC.getActualNumber());
        assertEquals(11, JC.getActualNumber());
        assertEquals(10, TC.getActualNumber());
        assertEquals(9, cardSix.getActualNumber());
    }
}
