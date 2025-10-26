/*
 * Course: CSC-1110
 * Assignment: Deck and Card Polymorphism
 * Name: FIXME
 * Last Updated: FIXME
 */
package hortona;

/**
 * A class representing a standard western playing card
 */
public class Card implements Comparable<Card> {
    private final Rank rank;
    private final Suit suit;

    /**
     * Constructor for a playing card
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + suit.toString().substring(0, 1);
    }

    /**
     * Compares this card to another card
     * @param o the other card
     * @return a positive number if this card is greater than o, 0 if the cards are equals,
     * and a negative number if this card is less than o.
     */
    public int compareTo(Card o) {
        return this.rank.getRank() - o.getRank().getRank();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Card that)) {
            return false;
        }
        return this.rank == that.rank && this.suit == that.suit;
    }
}
