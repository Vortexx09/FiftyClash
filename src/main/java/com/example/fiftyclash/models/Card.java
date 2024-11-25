package com.example.fiftyclash.models;

public class Card {
    private int value;
    private String suit;
    private String color;

    Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public String getColor() {
        return color;
    }

    public void SetColor(String color) {
        this.color = color;
    }
}
