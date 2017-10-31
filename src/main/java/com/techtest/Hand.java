package com.techtest;

import java.util.*;
import java.util.stream.Collectors;

public class Hand implements Comparable{
    private final HandType handType;

    private Hand(Builder builder) {
        this.handType = builder.getHandType();
    }

    public HandType getHandType() {
        return handType;
    }

    @Override
    public int compareTo(Object o) {
        int result = 0;

        if (o instanceof Hand) {
            Hand that = (Hand) o;
            result = this.getHandType().compareTo(that);
        }

        return result;
    }

    public static class Builder {
        private List<Card> orderedCardList;
        private HandType handType;

        private List<Card> getOrderedCardList() {
            return orderedCardList;
        }

        public Builder setOrderedCardList(List<Card> orderedCardList) {
            this.orderedCardList = orderedCardList;
            return this;
        }

        private HandType getHandType() {
            return handType;
        }

        private void setHandType(HandType handType) {
            this.handType = handType;
        }

        public Hand build() {
            Map<Integer, Long> map = buildDuplicatesMap();

            setHandType(parseHandType(map));

            return new Hand(this);
        }

        private Map<Integer, Long> buildDuplicatesMap() {
            return getOrderedCardList().stream()
                    .collect(Collectors.groupingBy(Card::getActualNumber, Collectors.counting()));
        }

        private HandType parseHandType(Map<Integer, Long> map) {
            HandType.Type typeOfHand = null;
            HandType handType = null;
            List<Integer> orderedComparableValuesList = new ArrayList();

            if (isStraightFlush()) {
                typeOfHand = HandType.Type.STRAIGHT_FLUSH;
                orderedComparableValuesList = buildComparableValuesListForStraight();
            } else if (isFourOfAKind(map)) {
                typeOfHand = HandType.Type.FOUR_OF_A_KIND;
                orderedComparableValuesList = buildComparableValuesListForFourOrThreeOfAKind(map, 4);
            }else if (isFullHouse(map)) {
                typeOfHand = HandType.Type.FULL_HOUSE;
                orderedComparableValuesList = buildComparableValuesListForFourOrThreeOfAKind(map, 3);
            } else if (isFlush()) {
                typeOfHand = HandType.Type.FLUSH;
                orderedComparableValuesList = buildComparableValuesListForSingles();
            } else if (isStraight()) {
                typeOfHand = HandType.Type.STRAIGHT;
                orderedComparableValuesList = buildComparableValuesListForStraight();
            } else if (hasThreeOfAKind(map)) {
                typeOfHand = HandType.Type.THREE_OF_A_KIND;
                orderedComparableValuesList = buildComparableValuesListForThreeOfAKindOrPairs(map, 3);
            } else if (numOfPairs(map) == 2) {
                typeOfHand = HandType.Type.TWO_PAIRS;
                orderedComparableValuesList = buildComparableValuesListForThreeOfAKindOrPairs(map, 2);
            } else if (numOfPairs(map) == 1) {
                typeOfHand = HandType.Type.ONE_PAIR;
                orderedComparableValuesList = buildComparableValuesListForThreeOfAKindOrPairs(map, 2);
            } else {
                typeOfHand = HandType.Type.SINGLES;
                orderedComparableValuesList = buildComparableValuesListForSingles();
            }

            return new HandType.Builder()
                    .setType(typeOfHand)
                    .setOrderedComparableValuesList(orderedComparableValuesList)
                    .build();
        }

        private List<Integer> buildComparableValuesListForStraight() {
            List<Integer> comparableValuesList = new ArrayList();

            // Dealing with A low straight: A(14), 5, 4, 3, 2
            if (getOrderedCardList().get(0).getActualNumber() == 14
                    && getOrderedCardList().get(4).getActualNumber() == 2) {
                comparableValuesList.add(Integer.valueOf(5));
            } else {
                comparableValuesList.add(Integer.valueOf(getOrderedCardList().get(0).getActualNumber()));
            }

            return comparableValuesList;
        }

        private List<Integer> buildComparableValuesListForFourOrThreeOfAKind(Map<Integer, Long> map, int numOfDuplicates) {
            List<Integer> comparableValuesList = new ArrayList();

            comparableValuesList.addAll(map.entrySet().stream()
                    .filter(integerLongEntry -> integerLongEntry.getValue().intValue() == numOfDuplicates)
                    .map(integerLongEntry -> integerLongEntry.getKey())
                    .collect(Collectors.toList()));

            comparableValuesList.addAll(map.entrySet().stream()
                    .filter(integerLongEntry -> integerLongEntry.getValue().intValue() != numOfDuplicates)
                    .map(integerLongEntry -> integerLongEntry.getKey())
                    .collect(Collectors.toList()));

            return comparableValuesList;
        }

        private List<Integer> buildComparableValuesListForThreeOfAKindOrPairs(Map<Integer, Long> map, int numOfDuplicates) {
            List<Integer> comparableValuesList = new ArrayList();

            // Add duplicates first
            for (Card card : getOrderedCardList()) {
                Integer cardValue = Integer.valueOf(card.getActualNumber());
                if (map.get(cardValue).intValue() == numOfDuplicates
                        && !comparableValuesList.contains(cardValue)) {
                    comparableValuesList.add(cardValue);
                }
            }

            // Add the rest of the cards, in order
            for (Card card : getOrderedCardList()) {
                if (!comparableValuesList.contains(Integer.valueOf(card.getActualNumber()))) {
                    comparableValuesList.add(Integer.valueOf(card.getActualNumber()));
                }
            }

            return comparableValuesList;
        }

        private List<Integer> buildComparableValuesListForSingles() {
            return getOrderedCardList().stream()
                    .map(card -> Integer.valueOf(card.getActualNumber()))
                    .collect(Collectors.toList());
        }

        private boolean isStraightFlush() {
            return isFlush() && isStraight();
        }

        private boolean isFullHouse(Map<Integer, Long> map) {
            return hasThreeOfAKind(map) && (numOfPairs(map) == 1);
        }

        private int numOfPairs(Map<Integer, Long> map) {
            int numOfPairs = 0;
            Iterator<Long> iterator = map.values().iterator();


            while (iterator.hasNext()) {
                if (iterator.next().intValue() == 2) {
                    numOfPairs ++;
                }
            }

            return numOfPairs;
        }

        private boolean hasThreeOfAKind(Map<Integer, Long> map) {
            return map.containsValue(Long.valueOf(3));
        }

        private boolean isFourOfAKind(Map<Integer, Long> map) {
            return map.containsValue(Long.valueOf(4));
        }

        private boolean isStraight() {
            boolean result = true;

            for (int i = 1; i < getOrderedCardList().size(); i ++) {
                Card prev = getOrderedCardList().get(i - 1);
                Card current = getOrderedCardList().get(i);
                int difference = prev.getActualNumber() - current.getActualNumber();

                // Need to handle the special case: A(14),5,4,3,2
                if (i == 1
                        && prev.getActualNumber() == 14
                        && current.getActualNumber() == 5) {
                    result = true;
                } else if (difference != 1) {
                    result = false;
                    break;
                }
            }

            return result;
        }

        private boolean isFlush() {
            return getOrderedCardList().stream().allMatch(card -> card.getSuit().getSuit().equals(Card.Suit.C.getSuit()))
                    || getOrderedCardList().stream().allMatch(card -> card.getSuit().getSuit().equals(Card.Suit.D.getSuit()))
                    || getOrderedCardList().stream().allMatch(card -> card.getSuit().getSuit().equals(Card.Suit.H.getSuit()))
                    || getOrderedCardList().stream().allMatch(card -> card.getSuit().getSuit().equals(Card.Suit.S.getSuit()));
        }
    }
}