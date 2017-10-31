package com.techtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class PokerHandsComparator {

    /**
     * Iterate through each line of input.
     */
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        PokerHandsComparator main = new PokerHandsComparator();
        while ((line = in.readLine()) != null) {
            System.out.println(line);

            int result = main.compareHands(line);
            main.printResult(result);
        }
    }

    public int compareHands(String line) {
        String leftHandString = line.substring(0, 15);
        String rightHandString = line.substring(15, 29);

        Hand leftHand = createHandFromString(leftHandString);
        Hand rightHand = createHandFromString(rightHandString);

        return compareHands(leftHand, rightHand);
    }

    public Hand createHandFromString(String line) {
        String[] cards = line.split(" ");
        int length = cards.length;
        List<Card> unorderedHandList = new ArrayList();

        for(int i = 0; i < length; i ++) {
            String value = String.valueOf(cards[i].charAt(0));
            String suit = String.valueOf(cards[i].charAt(1));
            unorderedHandList.add(buildCard(value, Card.Suit.valueOf(suit)));
        }

        return buildHand(unorderedHandList);
    }

    public List<Card> buildOrderedList(List<Card> unorderedList) {
        return unorderedList.stream().sorted((thisCard, thatCard) -> {
            int result = 0;

            if (thisCard.getActualNumber() < thatCard.getActualNumber()) {
                result = 1;
            } else if (thisCard.getActualNumber() > thatCard.getActualNumber()) {
                result = -1;
            }

            return result;

        }).collect(Collectors.toList());
    }

    private void printResult(int result) {
        if (result == 1) {
            System.out.println("Left");
        } else if (result == -1) {
            System.out.println("Right");
        } else {
            System.out.println("None");
        }
    }

    private int compareHands(Hand leftHand, Hand rightHand) {
        return leftHand.getHandType().compareTo(rightHand.getHandType());
    }

    public Card buildCard(String value, Card.Suit suit) {
        return new Card(value, suit);
    }

    public Hand buildHand(List<Card> cards) {
        return new Hand.Builder()
                .setOrderedCardList(buildOrderedList(cards))
                .build();
    }
}
