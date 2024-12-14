package com.example.fiftyclash.models;
/**
 * Represents a machine player in the game, extending the {@link Player} class.
 * This player automatically selects a card based on the current points to get as close as possible to 50 without exceeding it.
 */
public class Machine extends Player {

    /**
     * Constructs a new {@link Machine} player and initializes its hand cards.
     */
    public Machine() {
        handCards = getHandCards();
    }

    /**
     * Selects the best card to play based on the current points.
     * The machine tries to get as close as possible to 50 without exceeding it.
     *
     * @param currentPoints The current score in the game.
     * @return The index of the selected card, or -1 if no valid card can be played.
     */
    @Override
    public int selectPlayCard(int currentPoints) {
        int points = currentPoints;
        int selectedCardIndex = -1;
        int closestTo50 = Integer.MAX_VALUE;

        // Iterates through the player's hand to find the best card to play
        for (int i = 0; i < handCards.length; i++) {
            int cardValue = handCards[i].getCardValue(points);

            // If the card does not exceed 50, checks if it's the closest to 50
            if (points + cardValue <= 50) {
                int distanceTo50 = 50 - (points + cardValue);

                if (distanceTo50 < closestTo50) {
                    closestTo50 = distanceTo50;
                    selectedCardIndex = i;
                }
            }
        }

        // If no valid card can be played, prints a message
        if (selectedCardIndex == -1) {
            System.out.println("No valid card to play, player loses.");
        }

        return selectedCardIndex;
    }
}