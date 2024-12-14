package com.example.fiftyclash.models;

import com.example.fiftyclash.models.factory.HumanFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanFactoryTest {

    @Test
    void testCreatePlayer() {
        HumanFactory humanFactory = new HumanFactory();

        Player player = humanFactory.createPlayer();

        assertNotNull(player);
        assertInstanceOf(HumanPlayer.class, player);
    }

}