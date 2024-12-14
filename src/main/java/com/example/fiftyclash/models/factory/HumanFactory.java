package com.example.fiftyclash.models.factory;

import com.example.fiftyclash.models.HumanPlayer;
import com.example.fiftyclash.models.Player;

/**
 * A factory class responsible for creating human players in the game.
 * Extends the {@link PlayerFactory} abstract class to implement the creation of a {@link HumanPlayer}.
 */
public class HumanFactory extends PlayerFactory {

    /**
     * Creates a new instance of a human player.
     *
     * @return a {@link HumanPlayer} instance.
     */
    @Override
    public Player createPlayer() {
        return new HumanPlayer();
    }
}
