package com.example.fiftyclash.models;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    int machinesAmount;
    PlayerFactory humanFactory, machineFactory;
    String playerName;
    Player[] machines;
    Player humanPlayer;
    Table table;

    public GameModel(String playerName, int machinesAmount) {

        this.playerName = playerName;
        this.machinesAmount = machinesAmount;
        this.humanFactory = new HumanFactory();
        this.machineFactory = new MachineFactory();

        this.humanPlayer = humanFactory.createPlayer();
        machines = new Player[machinesAmount];
        table = new Table(humanPlayer, machines);

        for (int i = 0; i < machines.length; i++) {
            machines[i] = machineFactory.createPlayer();
        }

        table.initializeTable(humanPlayer, machines);

    }

    public Card[] getHumanCards() {
        return humanPlayer.getHandCards();
    }

    public Card getCurrentCard() {
        return table.getCurrentCard();
    }

    public void playCard(int index) {
        humanPlayer.playCard(index, table.getPlayDeck(), table.getDrawDeck());
    }

    public void setCurrentCard() {
        table.setCurrentCard();
    }

    public int playMachine(int index) {
        if (!isMachineActive(index)) {
            System.out.println("Machine " + index + " is not active.");
            return -1;
        }
        int selectedCard = machines[index].selectPlayCard(table.getPlayDeck());
        if (selectedCard == -1) {
            eliminatePlayer(index);
        } else {
            machines[index].playCard(selectedCard, table.getPlayDeck(), table.getDrawDeck());
            table.setCurrentCard();
        }

        return selectedCard;
    }

    public int getMachinesAmount() {
        return machinesAmount;
    }

    public int getCurrentPoints() {
        return table.getCurrentPoints();
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Integer> getActiveMachineIndexes() {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < machines.length; i++) {
            if (isMachineActive(i)) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public void eliminatePlayer(int index) {
        Card[] playerCards;
        if (index == 0) {
            playerCards = humanPlayer.getHandCards();
            returnCardsToDeck(playerCards);
        } else {
            playerCards = machines[index].getHandCards();
            returnCardsToDeck(playerCards);
            machines[index] = null;
            machinesAmount--;
            System.out.println("Machine " + index + " has been eliminated.");
        }

    }

    public boolean isMachineActive(int index) {
        return index >= 0 && index < machines.length && machines[index] != null;
    }

    public void returnCardsToDeck(Card[] cards) {
        table.returnCardsToDeck(cards);
    }

    public int getDeckSize() {
        return table.getDrawDeck().getSize();
    }
}