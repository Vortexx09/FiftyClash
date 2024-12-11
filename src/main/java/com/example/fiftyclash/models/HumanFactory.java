package com.example.fiftyclash.models;

public class HumanFactory extends PlayerFactory{

    @Override
    public Player createPlayer() {
        return new HumanPlayer();
    }
}
