package com.example.fiftyclash.models;

import java.util.ArrayList;

public abstract class Player {

    protected Card[] handCards;
    protected boolean canPlay;
    protected boolean isPlayerTurn;

    public Player() {
        handCards = new Card[4];
    }

    public Card[] getHandCards() {
        return handCards;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public void drawCard(Deck drawDeck) {
        for (int i = 0; i < 4; i++) {
            if (handCards[i] == null){
                handCards[i] = drawDeck.getDeck().get(0);
                drawDeck.removeCard(0);
            }
        }
    }

    public void playCard (int index, Deck playDeck, Deck drawDeck) {
        playDeck.addCard(handCards[index]);
        handCards[index] = null;
        drawCard(drawDeck);
    }

    public void printHandCards() {
        String icon = "";
        for (int i = 0; i < handCards.length; i++) {
            icon = handCards[i].getIcon();
            System.out.println(handCards[i].getValue() + icon);
        }
    }

    public int selectPlayCard(int currentPoints) {
        return 0;
    }
}