package com.example.fiftyclash.models.factory;

import com.example.fiftyclash.models.Player;

/**
 * Abstract class responsible for creating player instances.
 * This class defines the factory method for creating a {@link Player} object.
 * Specific player types will extend this class to provide the actual implementation for creating a player.
 */
public abstract class PlayerFactory {

    /**
     * Abstract method to create a player.
     * Subclasses should implement this method to create a specific type of player.
     *
     * @return A new instance of a {@link Player}.
     */
    public abstract Player createPlayer();
}

