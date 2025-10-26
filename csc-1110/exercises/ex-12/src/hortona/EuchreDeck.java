/*
 * Course: CSC-1110
 * Assignment: Deck and Card Polymorphism
 * Name: FIXME
 * Last Updated: FIXME
 */
package hortona;

/**
 * A classic Euchre deck
 */
public class EuchreDeck extends Deck {
    private static final int MAX_SIZE = 24;

    @Override
    public void addCard(Card card) {
        if (!contains(card) && isFull() && (card.getRank() == Rank.NINE ||
                card.getRank() == Rank.TEN || card.getRank() == Rank.JACK ||
                card.getRank() == Rank.QUEEN || card.getRank() == Rank.KING ||
                card.getRank() == Rank.ACE)) {
            cards.add(card);
        }
    }
    private boolean isFull() {
        return size() < MAX_SIZE;
    }
}
