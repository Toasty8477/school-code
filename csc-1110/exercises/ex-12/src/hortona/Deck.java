/*
 * Course: CSC-1110
 * Assignment: Deck and Card Polymorphism
 * Name: FIXME
 * Last Updated: FIXME
 */
package hortona;

import java.util.ArrayList;
import java.util.Collections;

/**
 * a deck of cards
 */
public abstract class Deck {
    protected ArrayList<Card> cards;

    protected Deck(){
        cards = new ArrayList<>();
    }

    /**
     * the size of the deck
     * @return the size of the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * adds a card to the deck
     * @param card any card
     */
    public abstract void addCard(Card card);

    /**
     * draws from the top of the deck
     * @return the drawn card
     */
    public Card drawCard() {
        return cards.remove(0);
    }

    /**
     * shuffles the deck
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * checks to see if the deck contains a card
     * @param card any card
     * @return true or false
     */
    public boolean contains(Card card) {
        boolean ret = false;
        for (int i = 0; i < cards.size() && !ret; i++) {
            if (cards.get(i).equals(card)) {
                ret = true;
            }
        }
        return ret;
    }
    @Override
    public String toString() {
        return cards.toString();
    }


}
