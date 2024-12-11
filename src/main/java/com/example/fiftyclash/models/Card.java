package com.example.fiftyclash.models;

import com.example.fiftyclash.models.cardAppearences.*;

public class Card {
    private String value;
    private CardAppearence appearence;

    public Card(String value, String suit) {
        this.value = value;
        switch (suit) {
            case "hearts":
                this.appearence = new HeartsAppearence();
                break;
            case "spades":
                this.appearence = new SpadesAppearence();
                break;
            case "clubs":
                this.appearence = new ClubsAppearence();
                break;
            case "diamonds":
                this.appearence = new DiamondsAppearence();
                break;
        }

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
        return appearence.getIcon();
    }

    public String getColor() {
        return appearence.getColor();
    }

    public String getValue(){
        return value;
    }
}
