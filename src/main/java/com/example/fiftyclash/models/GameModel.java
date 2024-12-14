package com.example.fiftyclash.models;

import com.example.fiftyclash.controllers.observers.Observer;
import com.example.fiftyclash.models.factory.HumanFactory;
import com.example.fiftyclash.models.factory.MachineFactory;
import com.example.fiftyclash.models.factory.PlayerFactory;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the main game model, managing the game's state, players, and interactions.
 * It serves as the core logic for the game, handling player actions, machine actions, and turn management.
 */
public class GameModel {

    /** The current points in the game. */
    private int currentPoints;

    /** The total number of machines in the game. */
    private int machinesAmount;

    /** Factory for creating the human player. */
    private PlayerFactory humanFactory;

    /** Factory for creating machine players. */
    private PlayerFactory machineFactory;

    /** The name of the human player. */
    private String playerName;

    /** Array of machine players. */
    private Player[] machines;

    /** The human player instance. */
    private Player humanPlayer;

    /** The game table that holds the decks and manages cards. */
    private Table table;

    /** List of observers to be notified of updates in the game state. */
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Constructs a new GameModel with the specified player name and number of machines.
     *
     * @param playerName     the name of the human player.
     * @param machinesAmount the number of machines in the game.
     */
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

    /**
     * Executes a turn for the specified machine.
     * If the machine cannot play a valid card, it is eliminated from the game.
     *
     * @param index the index of the machine taking its turn.
     * @return the index of the card played by the machine, or -1 if the machine is eliminated.
     */
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

    /**
     * Retrieves the indexes of all active machines.
     *
     * @return a list of indexes of active machines.
     */
    public List<Integer> getActiveMachineIndexes() {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < machines.length; i++) {
            if (isMachineActive(i)) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    /**
     * Eliminates a machine from the game, returning its cards to the draw deck.
     *
     * @param index the index of the machine to eliminate.
     */
    public void eliminatePlayer(int index) {
        Card[] playerCards = machines[index].getHandCards();
        returnCardsToDeck(playerCards);
        machines[index] = null;
        System.out.println("Machine " + index + " has been eliminated.");
    }

    /**
     * Updates the turn label with the current player's turn information.
     *
     * @param playerIndex the index of the player whose turn it is.
     */
    public void updateTurnLabel(int playerIndex) {
        String message;

        if (playerIndex == 0) {
            message = playerName + "'s turn";
        } else {
            message = "Machine's " + playerIndex + " turn";
        }

        notifyObservers(message);
    }

    /**
     * Checks if the given player can play any card without exceeding 50 points.
     *
     * @param currentPoints the current points in the game.
     * @param player        the player whose cards are to be checked.
     * @return true if the player can play a valid card, false otherwise.
     */
    public boolean checkCanPlay(int currentPoints, Player player) {
        for (Card card : player.getHandCards()) {
            if (card.getCardValue(currentPoints) + currentPoints <= 50) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the cards held by the human player.
     *
     * @return an array of cards held by the human player.
     */
    public Card[] getHumanCards() {
        return humanPlayer.getHandCards();
    }

    /**
     * Retrieves the current card on the table.
     *
     * @return the current card on the table.
     */
    public Card getCurrentCard() {
        return table.getCurrentCard();
    }

    /**
     * Plays the specified card from the human player's hand.
     *
     * @param index the index of the card to play.
     */
    public void playCard(int index) {
        humanPlayer.playCard(index, table.getPlayDeck(), table.getDrawDeck());
    }

    /**
     * Sets the current card on the table based on the play deck.
     */
    public void setCurrentCard() {
        table.setCurrentCard();
    }

    /**
     * Retrieves the total number of machines in the game.
     *
     * @return the total number of machines.
     */
    public int getMachinesAmount() {
        return machinesAmount;
    }

    /**
     * Updates the current points in the game by adding the specified value.
     *
     * @param currentPoints the value to add to the current points.
     */
    public void setCurrentPoints(int currentPoints) {
        this.currentPoints += currentPoints;
    }

    /**
     * Retrieves the current points in the game.
     *
     * @return the current points.
     */
    public int getCurrentPoints() {
        return currentPoints;
    }

    /**
     * Retrieves the name of the human player.
     *
     * @return the name of the human player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Checks if the specified machine is active in the game.
     *
     * @param index the index of the machine to check.
     * @return true if the machine is active, false otherwise.
     */
    public boolean isMachineActive(int index) {
        return index >= 0 && index < machines.length && machines[index] != null;
    }

    /**
     * Returns an array of cards to the draw deck.
     *
     * @param cards the cards to return to the deck.
     */
    public void returnCardsToDeck(Card[] cards) {
        table.returnCardsToDeck(cards);
    }

    /**
     * Retrieves the size of the draw deck.
     *
     * @return the size of the draw deck.
     */
    public int getDeckSize() {
        return table.getDrawDeck().getSize();
    }

    /**
     * Adds an observer to the list of observers.
     *
     * @param observer the observer to add.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer the observer to remove.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers with a given message.
     *
     * @param message the message to notify observers with.
     */
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /**
     * Updates the points label by notifying observers with the current points.
     *
     * @param points the current points to update the label with.
     */
    public void updatePointsLabel(int points) {
        notifyObservers(String.valueOf(points));
    }

    /**
     * Retrieves the game table.
     *
     * @return the game table.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Retrieves the human player.
     *
     * @return the human player instance.
     */
    public Player getHumanPlayer() {
        return humanPlayer;
    }

    /**
     * Retrieves the array of machine players.
     *
     * @return an array of machine players.
     */
    public Player[] getMachinePlayer() {
        return machines;
    }
}