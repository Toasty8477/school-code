/*
 * Course: CSC-1110
 * Assignment: Deck and Card Polymorphism
 * Name: FIXME
 * Last Updated: FIXME
 */
package hortona;

/**
 * An enumeration of the possible ranks for a playing card
 */
public enum Rank {
    /**
     * a numeric 2
     */
    TWO(2) {
        @Override
        public String toString() {
            return "2";
        }
    },
    /**
     * a numeric 3
     */
    THREE(3) {
        @Override
        public String toString() {
            return "3";
        }
    },
    /**
     * a numeric 4
     */
    FOUR(4) {
        @Override
        public String toString() {
            return "4";
        }
    },
    /**
     * a numeric 5
     */
    FIVE(5) {
        @Override
        public String toString() {
            return "5";
        }
    },
    /**
     * a numeric 6
     */
    SIX(6) {
        @Override
        public String toString() {
            return "6";
        }
    },
    /**
     * a numeric 7
     */
    SEVEN(7) {
        @Override
        public String toString() {
            return "7";
        }
    },
    /**
     * a numeric 8
     */
    EIGHT(8) {
        @Override
        public String toString() {
            return "8";
        }
    },
    /**
     * a numeric 9
     */
    NINE(9) {
        @Override
        public String toString() {
            return "9";
        }
    },
    /**
     * a numeric 10
     */
    TEN(10) {
        @Override
        public String toString() {
            return "10";
        }
    },
    /**
     * a jack, the lowest face card
     */
    JACK(11) {
        @Override
        public String toString() {
            return "J";
        }
    },
    /**
     * a queen, the middle face card
     */
    QUEEN(12) {
        @Override
        public String toString() {
            return "Q";
        }
    },
    /**
     * a king, the highest face card
     */
    KING(13) {
        @Override
        public String toString() {
            return "K";
        }
    },
    /**
     * an ace, the highest card
     */
    ACE(14) {
        @Override
        public String toString() {
            return "A";
        }
    };

    private final int rank;

    Rank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
