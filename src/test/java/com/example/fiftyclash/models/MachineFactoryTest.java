package com.example.fiftyclash.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachineFactoryTest {

    @Test
    void testCreatePlayer() {
        MachineFactory machineFactory = new MachineFactory();

        Player player = machineFactory.createPlayer();

        assertNotNull(player);
        assertInstanceOf(Machine.class, player);
    }

}