package com.example.fiftyclash.models;

public class Card {
    private String value;
    private String suit;
    private String color;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public void printCard(){
        String icon = "";
        switch (suit) {
            case "diamonds":
                icon = "♦";
                break;
            case "hearts":
                icon = "♥";
                break;
            case "clubs":
                icon = "♣";
                break;
            case "spades":
                icon = "♠";
                break;
        }
        System.out.println(value + icon);
    }

    public String getValue(){
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public int getCardValue() {
        if (value == "J" || value == "Q" || value == "K") {
            return 10;
        }
        else if (value == "A"){
            return 10;
        }
        else{
            return Integer.parseInt(value);
        }
    }

    public String getColor() {
        return color;
    }

    public void SetColor(String color) {
        this.color = color;
    }
}
