/*
 * Course: CSC-1110
 * Assignment: Deck and Card Polymorphism
 * Name: FIXME
 * Last Updated: FIXME
 */
package hortona;

/**
 * A classic poker deck
 */
public class PokerDeck extends Deck {
    private static final int MAX_SIZE = 52;

    @Override
    public void addCard(Card card) {
        if (!contains(card) && isFull()) {
            cards.add(card);
        }
    }
    private boolean isFull() {
        return size() < MAX_SIZE;
    }
}
