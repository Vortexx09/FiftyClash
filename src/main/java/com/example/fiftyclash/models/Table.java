package com.example.fiftyclash.models;

import java.util.Collections;

/**
 * Represents the game table, which holds the draw deck, play deck, and manages the players' cards.
 * This class is responsible for initializing the game, managing the deck of cards, and maintaining the state of the current card.
 */
public class Table {

    private Player humanPlayer;
    private Player[] machines;
    private Card currentCard;
    private Deck drawDeck;
    private Deck playDeck;

    /**
     * Constructor for initializing the table with a human player and a list of machine players.
     *
     * @param player The human player.
     * @param machinesList The array of machine players.
     */
    public Table(Player player, Player[] machinesList) {
        this.drawDeck = new Deck();
        this.humanPlayer = player;
        this.machines = machinesList;
        this.playDeck = new Deck();
    }

    /**
     * Initializes the game table by shuffling the deck and drawing cards for the players.
     * This method is called at the start of the game to set up the draw deck, play deck, and deal cards to the players.
     *
     * @param player The human player.
     * @param machinesList The array of machine players.
     */
    public void initializeTable(Player player, Player[] machinesList) {
        drawDeck.initializeDeck();

        // Draw cards for the human player
        for (int i = 0; i < player.getHandCards().length; i++){
            humanPlayer.drawCard(drawDeck);
        }

        // Draw cards for the machine players
        for (int i = 0; i < machinesList.length; i++) {
            for (int j = 0; j < machinesList[i].getHandCards().length; j++) {
                machinesList[i].drawCard(drawDeck);
            }
        }

        // Set the initial current card if not already set
        if (currentCard == null) {
            currentCard = drawDeck.getDeck().get(0);
            drawDeck.getDeck().remove(0);
            playDeck.getDeck().add(currentCard);
        }
    }

    /**
     * Sets the current card to the most recent card from the play deck.
     */
    public void setCurrentCard() {
        int index = playDeck.getDeck().size() - 1;
        currentCard = playDeck.getDeck().get(index);
    }

    /**
     * Refills the draw deck by moving all cards from the play deck back into the draw deck and shuffling them.
     */
    public void refillDeck(){
        for (int i = playDeck.getDeck().size() - 1; i > 0; i--) {
            drawDeck.addCard(playDeck.getDeck().get(i));
            playDeck.removeCard(i);
        }
        Collections.shuffle(drawDeck.getDeck());
    }

    /**
     * Returns the specified cards to the draw deck.
     *
     * @param cards The cards to be returned to the draw deck.
     */
    public void returnCardsToDeck(Card[] cards){
        for (Card card : cards) {
            drawDeck.getDeck().add(card);
        }
    }

    /**
     * Gets the draw deck.
     *
     * @return The draw deck.
     */
    public Deck getDrawDeck() {
        return drawDeck;
    }

    /**
     * Gets the play deck.
     *
     * @return The play deck.
     */
    public Deck getPlayDeck() {
        return playDeck;
    }

    /**
     * Gets the current card being played.
     *
     * @return The current card.
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * Gets the array of machine players.
     *
     * @return The machine players.
     */
    public Player[] getMachine(){
        return machines;
    }
}
