package com.example.fiftyclash.models;

public class Card {
    private String value;
    private String suit;
    private String icon;
    private String color;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getCardValue() {
        if (value == "J" || value == "Q" || value == "K") {
            return -10;
        }
        else if (value == "A"){
            return 10;
        }
        else{
            return Integer.parseInt(value);
        }
    }

    public String getIcon(){
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
        return icon;
    }

    public String getColor() {
        switch (icon){
            case "♦", "♥":
                color = "RED";
                break;
            case "♣", "♠":
                color = "BLACK";
                break;
        }
        return color;
    }

    public String getValue(){
        return value;
    }

    public String getSuit() {
        return suit;
    }
}
