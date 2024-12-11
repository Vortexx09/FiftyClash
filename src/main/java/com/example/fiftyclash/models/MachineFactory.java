package com.example.fiftyclash.models;

public class MachineFactory extends PlayerFactory{

    @Override
    public Player createPlayer() {
        return new Machine();
    }
}

