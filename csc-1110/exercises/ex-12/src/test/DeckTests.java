/*
 * Course: CSC-1110
 * Assignment: Deck and Card Polymorphism
 * Fall 2023
 */
package test;

import hortona.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Suite of tests for decks and cards
 */
public class DeckTests {
    private Card c1;
    private Card c2;
    private Card c3;
    private Card c4;
    private Card c5;
    private ArrayList<Deck> decks;

    /**
     * Initializes Cards for tests
     */
    @BeforeEach
    public void setup() {
        c1 = new Card(Rank.ACE, Suit.SPADES);
        c2 = new Card(Rank.ACE, Suit.SPADES);
        c3 = new Card(Rank.ACE, Suit.DIAMONDS);
        c4 = new Card(Rank.KING, Suit.SPADES);
        c5 = new Card(Rank.TWO, Suit.HEARTS);
        decks = new ArrayList<>();
    }

    /**
     * Tests construction of a card
     */
    @Test
    @DisplayName("Card Construction")
    public void cards() {
        Assertions.assertEquals(Rank.ACE, c1.getRank());
        Assertions.assertEquals(Suit.SPADES, c1.getSuit());
        Assertions.assertEquals(Rank.ACE, c2.getRank());
        Assertions.assertEquals(Suit.SPADES, c2.getSuit());
        Assertions.assertEquals(Rank.ACE, c3.getRank());
        Assertions.assertEquals(Suit.DIAMONDS, c3.getSuit());
        Assertions.assertEquals(Rank.KING, c4.getRank());
        Assertions.assertEquals(Suit.SPADES, c4.getSuit());
    }

    /**
     * Tests Card equals method
     */
    @Test
    @DisplayName("Card Equality")
    public void cardEquals() {
        Assertions.assertEquals(c1, c1);
        Assertions.assertEquals(c1, c2);
        Assertions.assertNotEquals(c1, c3);
        Assertions.assertNotEquals(c1, c4);
        Assertions.assertNotEquals(c2, c3);
        Assertions.assertNotEquals(c2, c4);
        Assertions.assertNotEquals(c3, c4);
    }

    /**
     * Tests Card compareTo
     */
    @Test
    @DisplayName("Card Comparisons")
    public void cardCompare() {
        Assertions.assertEquals(0, c1.compareTo(c2));
        Assertions.assertTrue(0 < c1.compareTo(c4));
        Assertions.assertTrue(0 > c4.compareTo(c3));
    }

    /**
     * Tests Card toString
     */
    @Test
    @DisplayName("Card toString")
    public void cardToString() {
        Assertions.assertEquals("AS", c1.toString());
        Assertions.assertEquals("AD", c3.toString());
        Assertions.assertEquals("KS", c4.toString());
    }

    /**
     * Test construction of a deck
     */
    @Test
    @DisplayName("Deck construction")
    public void decks() {
        PokerDeck pd = new PokerDeck();
        EuchreDeck ed = new EuchreDeck();
        decks.add(pd);
        decks.add(ed);
    }

    /**
     * Test Deck size
     */
    @Test
    @DisplayName("Deck size")
    public void deckSize() {
        PokerDeck pd = new PokerDeck();
        Assertions.assertEquals(0, pd.size());
        pd.addCard(c1);
        Assertions.assertEquals(1, pd.size());
    }

    /**
     * Test Deck add
     */
    @Test
    @DisplayName("Deck add")
    public void deckAdd() {
        PokerDeck pd = new PokerDeck();
        EuchreDeck ed = new EuchreDeck();
        pd.addCard(c1);
        ed.addCard(c1);
        Assertions.assertEquals(1, pd.size());
        Assertions.assertEquals(1, ed.size());
        pd.addCard(c2);
        ed.addCard(c2);
        Assertions.assertEquals(1, pd.size());
        Assertions.assertEquals(1, ed.size());
        pd.addCard(c3);
        ed.addCard(c3);
        Assertions.assertEquals(2, pd.size());
        Assertions.assertEquals(2, ed.size());
        pd.addCard(c4);
        ed.addCard(c4);
        Assertions.assertEquals(3, pd.size());
        Assertions.assertEquals(3, ed.size());
        pd.addCard(c5);
        ed.addCard(c5);
        Assertions.assertEquals(4, pd.size());
        Assertions.assertEquals(3, ed.size());
    }

    /**
     * Test Deck remove
     */
    @Test
    @DisplayName("Deck remove")
    public void deckRemove() {
        Deck pd = buildDeck();
        Assertions.assertEquals(c1, pd.drawCard());
        Assertions.assertEquals(3, pd.size());
        Assertions.assertEquals(c3, pd.drawCard());
        Assertions.assertEquals(2, pd.size());
        Assertions.assertEquals(c4, pd.drawCard());
        Assertions.assertEquals(1, pd.size());
        Assertions.assertEquals(c5, pd.drawCard());
        Assertions.assertEquals(0, pd.size());
        Assertions.assertThrows(IndexOutOfBoundsException.class, pd::drawCard);
    }

    /**
     * Tests Card toString
     */
    @Test
    @DisplayName("Deck toString")
    public void deckToString() {
        Deck deck = buildDeck();
        Assertions.assertEquals("[AS, AD, KS, 2H]", deck.toString());
        deck.drawCard();
        Assertions.assertEquals("[AD, KS, 2H]", deck.toString());
    }

    /**
     * Tests Deck shuffling
     */
    @Test
    @DisplayName("Deck Shuffling")
    public void deckShuffle() {
        Deck pd = buildDeck();
        String before = pd.toString();
        pd.shuffle();
        String after = pd.toString();
        Assertions.assertNotEquals(before, after);
    }

    private Deck buildDeck() {
        PokerDeck pd = new PokerDeck();
        pd.addCard(c1);
        pd.addCard(c3);
        pd.addCard(c4);
        pd.addCard(c5);
        return pd;
    }
}
