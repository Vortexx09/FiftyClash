package com.example.fiftyclash.models;

import java.util.ArrayList;
import java.util.Collections;

public class Table {
    private Player[] players;
    private Card currentCard;
    private Deck drawDeck;
    private Deck playDeck;

    public Table(Deck drawDeck, Player[] players) {
        this.drawDeck = drawDeck;
        this.playDeck = new Deck();
    }

    public void initializeTable(Player[] players) {
        for (Player player : players) {
            for (int j = 0; j < 4; j++) {
                player.drawCard(drawDeck);
            }
        }

        if (currentCard == null) {
            currentCard = drawDeck.getDeck().get(0);
            playDeck.getDeck().add(currentCard);
            drawDeck.getDeck().remove(0);
        }
    }

    public void updateCurrentCard() {
        int index = playDeck.getDeck().size() - 1;
        currentCard = playDeck.getDeck().get(index);
    }

    public void refillDeck(){
        if (drawDeck.getDeck().isEmpty()) {
            for (int i = playDeck.getDeck().size() - 1; i > 0; i--) {
                drawDeck.getDeck().add(i, playDeck.getDeck().get(i));
                playDeck.getDeck().remove(i);
            }
        }
        Collections.shuffle(drawDeck.getDeck());
    }

    public void checkCurrentPoints(Player player) {
        if (playDeck.getCurrentPoints() > 50){
            player.setCanPlay(false);
        }
    }

    public Deck getDrawDeck() {
        return drawDeck;
    }

    public Deck getPlayDeck() {
        return playDeck;
    }

    public Card getCurrentCard() {
        return currentCard;
    }
}
