package com.example.fiftyclash.models;
/**
 * Represents a human player in the game, extending the {@link Player} class.
 * This class initializes the player's hand with a set of cards.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a new {@link HumanPlayer} and initializes the player's hand with 4 cards.
     */
    public HumanPlayer() {
        handCards = new Card[4];
    }
}
