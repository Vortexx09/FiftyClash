package com.example.fiftyclash.models;

import java.util.ArrayList;

public class Player {
    private Card[] handCards;
    private boolean canPlay;
    private boolean isPlayerTurn;

    public Player() {
        handCards = new Card[4];
    }

    public void drawCard(Deck deck) {
        for (int i = 0; i < 4; i++) {
            if (handCards[i] == null){
                handCards[i] = deck.getDeck().get(0);
                deck.removeCard(0);
            }
        }
    }

    public void playCard(Deck deck, int index) {
        if (deck.getCurrentPoints() + handCards[index].getCardValue() < 50){
            deck.addCard(handCards[index]);
            handCards[index] = null;
        }
    }

    public void printHandCards() {
        String icon = "";
        for (int i = 0; i < handCards.length; i++) {
            switch (handCards[i].getSuit()){
                case "diamonds": icon = "♦"; break;
                case "hearts": icon = "♥"; break;
                case "clubs": icon = "♣"; break;
                case "spades": icon = "♠"; break;
            }
            System.out.println(handCards[i].getValue() + icon);
        }
    }

    public Card[] getHandCards() {
        return handCards;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean getCanPlay() {
        return canPlay;
    }
}
