package com.example.fiftyclash.models.cardAppearences;

/**
 * This interface allows to implement the bridge structural pattern.
 */
public interface CardAppearence {

    /**
     Return the card icon
     * @return icon.
     */
    String getIcon();

    /**
     Return the card icon
     * @return color.
     */
    String getColor();

}
