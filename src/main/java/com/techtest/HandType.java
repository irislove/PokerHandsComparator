package com.techtest;

import java.util.Iterator;
import java.util.List;

public class HandType implements Comparable {

    public enum Type {
        STRAIGHT_FLUSH(8),
        FOUR_OF_A_KIND(7),
        FULL_HOUSE(6),
        FLUSH(5),
        STRAIGHT(4),
        THREE_OF_A_KIND(3),
        TWO_PAIRS(2),
        ONE_PAIR(1),
        SINGLES(0);

        private final int ranking;

        Type(int ranking) {
            this.ranking = ranking;
        }

        public int getRanking() {
            return ranking;
        }
    }

    final private Type type;
    final private List<Integer> orderedComparableValuesList;

    private HandType(Builder builder) {
        this.type = builder.getType();
        this.orderedComparableValuesList = builder.getOrderedComparableValuesList();
    }

    public Type getType() {
        return type;
    }

    public List<Integer> getOrderedComparableValuesList() {
        return orderedComparableValuesList;
    }

    @Override
    public int compareTo(Object o) {
        int result = 0;

        if (o instanceof HandType) {
            HandType that = (HandType) o;

            if (this.getType().getRanking() > that.getType().getRanking()) {
                result = 1;
            } else if (this.getType().getRanking() < that.getType().getRanking()) {
                result = -1;
            } else {
                result = compareEachCardValues(getOrderedComparableValuesList(), that.getOrderedComparableValuesList());
            }
        }

        return result;
    }

    private int compareEachCardValues(List<Integer> thisList, List<Integer> thatList) {
        int result = 0;

        Iterator<Integer> thisIterator = thisList.iterator();
        Iterator<Integer> thatIterator = thatList.iterator();

        //Compare each card, until a difference is found
        while(thisIterator.hasNext() && thatIterator.hasNext()) {
            int cardResult = thisIterator.next().compareTo(thatIterator.next());
            if (cardResult != 0) {
                result = cardResult;
                break;
            }
        }

        return result;
    }

    public static class Builder {
        private Type type;
        private List<Integer> orderedComparableValuesList;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setOrderedComparableValuesList(List<Integer> orderedComparableValuesList) {
            this.orderedComparableValuesList = orderedComparableValuesList;
            return this;
        }

        private Type getType() {
            return this.type;
        }

        private List<Integer> getOrderedComparableValuesList() {
            return this.orderedComparableValuesList;
        }

        public HandType build() {
            return new HandType(this);
        }
    }
}
