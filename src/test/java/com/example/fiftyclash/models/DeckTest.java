package com.example.fiftyclash.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckWithoutInitialization() {
        Deck deck = new Deck();
        assertTrue(deck.getDeck().isEmpty());
    }

    @Test
    void testDeckWithInitialization() {
        Deck deck = new Deck();
        deck.initializeDeck();

        int expectedSize = 52;

        assertEquals(expectedSize, deck.getDeck().size());
    }

    @Test
    void testAddCardToDeck() {
        Deck deck = new Deck();
        Card cardToAdd = new Card("9", "Hearts");

        deck.addCard(cardToAdd);

        assertTrue(deck.getDeck().contains(cardToAdd));
    }
    
    @Test
    void testAddMultipleCardsToDeck() {
        Deck deck = new Deck();

        List<Card> cardsToAdd = add4CardsToDeck(deck);

        for (Card card : cardsToAdd) {
            assertTrue(deck.getDeck().contains(card));
        }
    }

    @Test
    void testRemoveCardFromDeck() {
        Deck deck = new Deck();
        Card cardToAdd = new Card("9", "Hearts");

        deck.addCard(cardToAdd);

        if (deck.getDeck().contains(cardToAdd)) {
            deck.removeCard(0);
        }

        assertFalse(deck.getDeck().contains(cardToAdd));
    }

    List<Card> add4CardsToDeck(Deck deck) {
        List<Card> cardsToAdd = new ArrayList<>();

        cardsToAdd.add(new Card("9", "Hearts"));
        cardsToAdd.add(new Card("K", "Hearts"));
        cardsToAdd.add(new Card("A", "Clubs"));
        cardsToAdd.add(new Card("10", "Spades"));

        for (Card card : cardsToAdd) {
            deck.addCard(card);
        }

        return cardsToAdd;
    }



}