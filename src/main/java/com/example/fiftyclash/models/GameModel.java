package com.example.fiftyclash.models;

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
        int selectedCard = machines[index].selectPlayCard(table.getPlayDeck());
        machines[index].playCard(selectedCard, table.getPlayDeck(), table.getDrawDeck());
        table.setCurrentCard();
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
}
