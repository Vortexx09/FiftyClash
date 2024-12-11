package com.example.fiftyclash.models;

import java.util.Collections;

public class Table {
    private Player humanPlayer;
    private Player[] machines;
    private Card currentCard;
    private Deck drawDeck;
    private Deck playDeck;

    public Table(Player player, Player[] machinesList) {
        this.drawDeck = new Deck();
        this.humanPlayer = player;
        this.machines = machinesList;
        this.playDeck = new Deck();
    }

    public void initializeTable(Player player, Player[] machinesList) {
        drawDeck.initializeDeck();
        drawDeck.printDeck();

        for (int i = 0; i < player.getHandCards().length; i++){
            humanPlayer.drawCard(drawDeck);
        }

        for (int i = 0; i < machinesList.length; i++) {
            for (int j = 0; j < machinesList[i].getHandCards().length; j++) {
                machinesList[i].drawCard(drawDeck);
            }
        }

        if (currentCard == null) {
            currentCard = drawDeck.getDeck().get(0);
            drawDeck.getDeck().remove(0);
            playDeck.getDeck().add(currentCard);
        }
    }

    public void setCurrentCard() {
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
            humanPlayer.setCanPlay(false);
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

    public Player[] getMachine(){
        return machines;
    }
}
