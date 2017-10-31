package com.techtest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {
    private PokerHandsComparator main;

    @Before
    public void setUp() {
        main = new PokerHandsComparator();
    }

    @Test
    public void testStraightFlushWithA() {
        String line = "5S 2S AS 4S 3S";
        Hand hand = main.createHandFromString(line);

        assertEquals(HandType.Type.STRAIGHT_FLUSH, hand.getHandType().getType());
    }

    @Test
    public void testStraightFlushNormal() {
        String line = "5S 2S 6S 4S 3S";
        Hand hand = main.createHandFromString(line);

        assertEquals(HandType.Type.STRAIGHT_FLUSH, hand.getHandType().getType());
    }

    @Test
    public void testFourOfAKind() {
        String line = "AC AD AH JH AS";
        Hand hand = main.createHandFromString(line);

        assertEquals(HandType.Type.FOUR_OF_A_KIND, hand.getHandType().getType());
    }

    @Test
    public void testFullHouse() {
        String line = "AC JD AH JH AS";
        Hand leftHand = main.createHandFromString(line);

        assertEquals(HandType.Type.FULL_HOUSE, leftHand.getHandType().getType());
    }

    @Test
    public void testFlush() {
        String line = "AC 6C 8C 4C JC";
        Hand hand = main.createHandFromString(line);

        assertEquals(HandType.Type.FLUSH, hand.getHandType().getType());
    }

    @Test
    public void testStraight() {
        String line = "7H 5C 9S 8D 6S";
        Hand hand = main.createHandFromString(line);

        assertEquals(HandType.Type.STRAIGHT, hand.getHandType().getType());
    }

    @Test
    public void testThreeOfAKind() {
        String line = "AC AD TH JH AS";
        Hand leftHand = main.createHandFromString(line);

        assertEquals(HandType.Type.THREE_OF_A_KIND, leftHand.getHandType().getType());
    }

    @Test
    public void testTwoPairs() {
        String line = "AC JD TH JH AS";
        Hand leftHand = main.createHandFromString(line);

        assertEquals(HandType.Type.TWO_PAIRS, leftHand.getHandType().getType());
    }

    @Test
    public void testOnePair() {
        String line = "AC JD TH 2H AS";
        Hand leftHand = main.createHandFromString(line);

        assertEquals(HandType.Type.ONE_PAIR, leftHand.getHandType().getType());
    }

    @Test
    public void testSingles() {
        String line = "AC JD TH 2H 5S";
        Hand leftHand = main.createHandFromString(line);

        assertEquals(HandType.Type.SINGLES, leftHand.getHandType().getType());
    }

//    @Test
//    public void testCompareCardValuesEquals() {
//        String leftHandString = "4H 9S 7C 7S 2D";
//        String rightHandString = "7H 4S 2H 7C 9C";
//
//        Hand rightHand = main.createHandFromString(rightHandString);
//        Hand leftHand = main.createHandFromString(leftHandString);
//
//        assertEquals(0, leftHand.compareEachCardValuesInCardList(rightHand));
//    }
}
