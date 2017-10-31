package com.techtest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PokerHandsComparatorTest {
    private PokerHandsComparator main;

    @Before
    public void setUp() {
        this.main = new PokerHandsComparator();
    }

    @Test
    public void testCreateHandFromString() {
        String line = "AC JD 2C 5H 6S";
        Hand createdHand = main.createHandFromString(line);

        List<Card> handList = new ArrayList();
        Card cardOne = main.buildCard("A", Card.Suit.C);
        Card cardTwo = main.buildCard("J", Card.Suit.D);
        Card cardThree = main.buildCard("2", Card.Suit.C);
        Card cardFour = main.buildCard("5", Card.Suit.H);
        Card cardFive = main.buildCard("6", Card.Suit.S);

        handList.add(cardOne);
        handList.add(cardTwo);
        handList.add(cardThree);
        handList.add(cardFour);
        handList.add(cardFive);

        Hand expectedHand = main.buildHand(handList);

        assertEquals(0, expectedHand.getHandType().compareTo(createdHand.getHandType()));
    }

    @Test
    public void testEqualHands() {
        String line = "4H 9S 7C 7S 2D 7H 4S 2H 7C 9C";
        int result = main.compareHands(line);

        assertEquals(0, result);
    }

    @Test
    public void testFullHouseVsTwoPairs() {
        String line = "JC 8S JH 8C JS 9H 3C 4H 3S 9S";
        int result = main.compareHands(line);

        assertEquals(1, result);
    }

    @Test
    public void testStraightFlushVsFlush() {
        String line = "AS 2S 3S 4S 5S KC JC QC TC 6C";
        int result = main.compareHands(line);

        assertEquals(1, result);
    }

    @Test
    public void testTwoStraightFlushes() {
        String line = "AS 2S 3S 4S 5S 2C 3C 4C 5C 6C";
        int result = main.compareHands(line);

        assertEquals(-1, result);
    }

    @Test
    public void testTwoStraights() {
        String line = "AS 2C 3S 4S 5S TC JC QC KC AS";
        int result = main.compareHands(line);

        assertEquals(-1, result);
    }

    @Test
    public void testTwoFlushes() {
        String line = "2H 8H 3H 2H 7H 8C 9C TC 2C AC";
        int result = main.compareHands(line);

        assertEquals(-1, result);
    }

    @Test
    public void testSingles() {
        String line = "2C 3H 4S 5C 7H 8C 5S 4H 3C 2S";
        int result = main.compareHands(line);

        assertEquals(-1, result);
    }

    @Test
    public void testTwoFullHouses() {
        String line = "8C AH 8S 8C AD JH 9H 9C 9S JS";
        int result = main.compareHands(line);

        assertEquals(-1, result);
    }

    @Test
    public void testTwoFourOfAKinds() {
        String line = "7C 7S 8H 7H 7D JC 4C 4H 4S 4D";
        int result = main.compareHands(line);

        assertEquals(1, result);
    }

    @Test
    public void testTwoTwoPairs() {
        String line = "JC JH 8C 7S 7D JS JD 9S 6H 6D";
        int result = main.compareHands(line);

        assertEquals(1, result);
    }
}
