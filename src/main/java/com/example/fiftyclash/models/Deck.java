package com.example.fiftyclash.models;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Represents a deck of playing cards.
 * Provides methods to initialize, shuffle, add, remove, and retrieve cards.
 */
public class Deck {

    /** The list of cards in the deck. */
    private ArrayList<Card> deck;

    /**
     * Constructs an empty deck of cards.
     */
    public Deck() {
        deck = new ArrayList<>();
    }

    /**
     * Initializes the deck with a full set of 52 standard playing cards.
     * Each card is created for all suits ("diamonds", "hearts", "clubs", "spades")
     * and values ("A", "2", ..., "K"), then the deck is shuffled.
     */
    public void initializeDeck() {
        String[] suits = {"diamonds", "hearts", "clubs", "spades"};
        String[] value = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < value.length; j++) {
                deck.add(new Card(value[j], suits[i]));
            }
        }
        Collections.shuffle(deck);
    }

    /**
     * Checks whether the deck is empty.
     *
     * @return {@code true} if the deck contains no cards, {@code false} otherwise.
     */
    public boolean deckIsEmpty() {
        return deck.isEmpty();
    }

    /**
     * Adds a card to the deck.
     *
     * @param card the card to be added to the deck.
     */
    public void addCard(Card card) {
        deck.add(card);
    }

    /**
     * Removes a card from the deck at the specified index.
     *
     * @param index the index of the card to remove.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void removeCard(int index) {
        deck.remove(index);
    }

    /**
     * Retrieves the list of cards in the deck.
     *
     * @return an {@code ArrayList} of cards in the deck.
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Retrieves the size of the deck.
     *
     * @return the number of cards currently in the deck.
     */
    public int getSize() {
        return deck.size();
    }
}