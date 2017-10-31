package com.techtest;

import java.util.Objects;

public class Card implements Comparable {
    public enum Suit {
        D("Diamond"),
        H("Heart"),
        S("Spade"),
        C("Club");

        private final String suit;

        Suit(String suit) {
            this.suit = suit;
        }

        public String getSuit() {
            return this.suit;
        }
    }

    private final String value;
    private final Suit suit;

    public Card(String value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public String getValue() {
        return this.value;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public int getActualNumber() {
        int actualNumber = 0;

        // 2, 3, 4, 5, 6, 7, 8, 9, Ten=10, Jack=11, Queen=12, King=13, Ace=14
        switch(this.value) {
            case "T":
                actualNumber = 10;
                break;
            case "J":
                actualNumber = 11;
                break;
            case "Q":
                actualNumber = 12;
                break;
            case "K":
                actualNumber = 13;
                break;
            case "A":
                actualNumber = 14;
                break;
            default:
                actualNumber = Integer.valueOf(this.value);
                break;
        }

        return actualNumber;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj == null) {
            result = this == null;
        } else if (obj instanceof Card) {
            Card that = (Card) obj;
            result = (this.getValue().equals(that.getValue()) && this.getSuit().equals(that.getSuit()));
        }

        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }

    @Override
    public int compareTo(Object obj) {
        int result = 0;

        if (obj instanceof Card) {
            Card that = (Card) obj;

            if (this.getActualNumber() == that.getActualNumber()) {
                result = 0;
            } else if (this.getActualNumber() < that.getActualNumber()) {
                result = -1;
            } else if (this.getActualNumber() > that.getActualNumber()) {
                result = 1;
            }
        }

        return result;
    }
}
