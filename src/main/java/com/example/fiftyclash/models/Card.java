package com.example.fiftyclash.models;

import com.example.fiftyclash.models.cardAppearences.*;
/**
 * Represents a playing card with a value and appearance.
 * The card can belong to one of four suits (hearts, spades, clubs, diamonds)
 * and can have a numeric or face value. It provides methods to retrieve
 * its value, appearance, and behavior based on the game's rules.
 */
public class Card {

    /** The value of the card (e.g., "A", "2", "J"). */
    private String value;

    /** The appearance of the card, including suit and color. */
    private CardAppearence appearence;

    /**
     * Constructs a new card with the specified value and suit.
     * The suit determines the appearance of the card.
     *
     * @param value the value of the card (e.g., "A", "2", "J").
     * @param suit  the suit of the card ("hearts", "spades", "clubs", "diamonds").
     */
    public Card(String value, String suit) {
        this.value = value;
        switch (suit) {
            case "hearts":
                this.appearence = new HeartsAppearence();
                break;
            case "spades":
                this.appearence = new SpadesAppearence();
                break;
            case "clubs":
                this.appearence = new ClubsAppearence();
                break;
            case "diamonds":
                this.appearence = new DiamondsAppearence();
                break;
        }
    }

    /**
     * Calculates the numeric value of the card based on its type and the
     * current points in the game. The value may vary for face cards,
     * aces, and other special cards.
     *
     * @param points the current points in the game to determine the ace's value.
     * @return the numeric value of the card. Face cards (J, Q, K) return -10,
     *         "A" returns 1 or 10 depending on the game's points, "9" returns 0,
     *         and numeric cards return their integer value.
     */
    public int getCardValue(int points) {
        if (value.equals("J") || value.equals("Q") || value.equals("K")) {
            return -10;
        } else if (value.equals("A")) {
            if (points + 10 > 50) return 1;
            else return 10;
        } else if (value.equals("9")) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }

    /**
     * Retrieves the icon representing the card's appearance.
     *
     * @return a string representing the card's icon (e.g., suit symbol).
     */
    public String getIcon() {
        return appearence.getIcon();
    }

    /**
     * Retrieves the color associated with the card's suit.
     *
     * @return a string representing the color of the card (e.g., "red" or "black").
     */
    public String getColor() {
        return appearence.getColor();
    }

    /**
     * Retrieves the value of the card.
     *
     * @return a string representing the value of the card (e.g., "A", "2", "J").
     */
    public String getValue() {
        return value;
    }
}