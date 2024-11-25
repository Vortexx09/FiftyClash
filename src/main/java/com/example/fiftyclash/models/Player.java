package com.example.fiftyclash.models;

import java.util.ArrayList;

public class Player {
    private Card selectedCard;
    private ArrayList<Card> handCards;
    private boolean isPlaying;

    Player(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }
}
