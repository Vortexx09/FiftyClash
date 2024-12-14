package com.example.fiftyclash.models;

import java.util.ArrayList;
/**
 * Abstract class representing a player in the game.
 * A player has a hand of cards and the ability to draw and play cards.
 */
public abstract class Player {

    /**
     * Array holding the cards in the player's hand.
     */
    protected Card[] handCards;

    /**
     * Indicates whether the player is able to play.
     */
    protected boolean canPlay = true;

    /**
     * Indicates whether it is the player's turn.
     */
    protected boolean isPlayerTurn;

    /**
     * Constructor for the Player class. Initializes the player's hand with 4 cards.
     */
    public Player() {
        handCards = new Card[4];
    }

    /**
     * Gets the player's current hand of cards.
     *
     * @return An array of {@link Card} objects representing the player's hand.
     */
    public Card[] getHandCards() {
        return handCards;
    }

    /**
     * Draws a card from the provided draw deck and adds it to the player's hand.
     * It will fill any empty slots in the hand.
     *
     * @param drawDeck The deck from which cards are drawn.
     */
    public void drawCard(Deck drawDeck) {
        for (int i = 0; i < 4; i++) {
            if (handCards[i] == null){
                handCards[i] = drawDeck.getDeck().get(0);
                drawDeck.removeCard(0);
            }
        }
    }

    /**
     * Plays a card from the player's hand by moving it to the play deck
     * and drawing a new card from the draw deck to replace it.
     *
     * @param index The index of the card to be played in the player's hand.
     * @param playDeck The deck where the played card is added.
     * @param drawDeck The deck from which a new card is drawn to replace the played card.
     */
    public void playCard (int index, Deck playDeck, Deck drawDeck) {
        playDeck.addCard(handCards[index]);
        handCards[index] = null;
        drawCard(drawDeck);
    }

    /**
     * Prints the cards in the player's hand to the console.
     * Displays the value and icon of each card in the hand.
     */
    public void printHandCards() {
        String icon = "";
        for (int i = 0; i < handCards.length; i++) {
            icon = handCards[i].getIcon();
            System.out.println(handCards[i].getValue() + icon);
        }
    }

    /**
     * Selects a card to play based on the current points. This method is overridden by specific player types.
     *
     * @param currentPoints The current points value used to determine the best card to play.
     * @return The index of the selected card to play, or 0 by default.
     */
    public int selectPlayCard(int currentPoints) {
        return 0;
    }

    /**
     * Sets whether the player is allowed to play.
     *
     * @param canPlay A boolean indicating whether the player can play.
     */
    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    /**
     * Gets whether the player is allowed to play.
     *
     * @return A boolean indicating whether the player can play.
     */
    public boolean getCanPlay() {
        return canPlay;
    }
}