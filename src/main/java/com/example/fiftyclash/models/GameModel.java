package com.example.fiftyclash.models;

import com.example.fiftyclash.controllers.observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    int currentPoints;
    int machinesAmount;
    PlayerFactory humanFactory, machineFactory;
    String playerName;
    Player[] machines;
    Player humanPlayer;
    Table table;

    private final List<Observer> observers = new ArrayList<>();

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

    public int playMachine(int index) {
        if (!isMachineActive(index)) {
            System.out.println("Machine " + index + " is not active.");
            return -1;
        }
        int selectedCard = machines[index].selectPlayCard(currentPoints);
        if (selectedCard == -1) {
            eliminatePlayer(index);
        } else {
            machines[index].playCard(selectedCard, table.getPlayDeck(), table.getDrawDeck());
            table.setCurrentCard();
        }

        return selectedCard;
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
        playerCards = machines[index].getHandCards();
        returnCardsToDeck(playerCards);
        machines[index] = null;
        System.out.println("Machine " + index + " has been eliminated.");
    }

    public void updateTurnLabel(int playerIndex) {
        String message;

        if (playerIndex == 0) {
            message = playerName + "'s turn";
        } else {
            message = "Machine's " + playerIndex + " turn";
        }

        notifyObservers(message);
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

    public int getMachinesAmount() {
        return machinesAmount;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints += currentPoints;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public String getPlayerName() {
        return playerName;
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

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void updatePointsLabel(int points) {
        notifyObservers(String.valueOf(points));
    }

    public Table getTable(){
        return table;
    }
}