package com.example.fiftyclash.models.factory;

import com.example.fiftyclash.models.Machine;
import com.example.fiftyclash.models.Player;

/**
 * Factory class responsible for creating instances of {@link Machine} players.
 * It extends the {@link PlayerFactory} class.
 */
public class MachineFactory extends PlayerFactory {

    /**
     * Creates and returns a new {@link Machine} player.
     *
     * @return A new {@link Machine} player instance.
     */
    @Override
    public Player createPlayer() {
        return new Machine();
    }
}
