package com.example.fiftyclash.models;

import java.util.ArrayList;

public class Player {
    private Card[] handCards;
    private boolean canPlay;
    private boolean isPlayerTurn;

    public Player() {
        handCards = new Card[4];
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

}
