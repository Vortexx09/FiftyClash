package com.example.fiftyclash.controllers;

import com.example.fiftyclash.controllers.observers.PointsObserver;
import com.example.fiftyclash.controllers.observers.TurnObserver;
import com.example.fiftyclash.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
/**
 * The GameController class handles the game logic, user interaction, and updates the UI components.
 * It manages player turns, machine turns, and the interaction with the game model.
 */
public class GameController {

    /** Root layout for the game UI. */
    @FXML
    private AnchorPane root;

    /** Currently selected card by the player. */
    private AnchorPane selectedCard;

    /** Labels for displaying points and turn information. */
    @FXML
    private Label pointsLabel1, turnsLabel;

    /** Buttons for controlling the game actions. */
    @FXML
    private Button playButton, startButton;

    /** Index of the selected card. */
    int cardIndex;

    /** The game model managing game state and logic. */
    GameModel game;

    /**
     * Handles the card selection by the player.
     * Highlights the selected card and enables the play button.
     *
     * @param event the MouseEvent triggered when a card is clicked.
     */
    @FXML
    public void getCardValues(MouseEvent event) {
        AnchorPane clickedCard = (AnchorPane) event.getSource();
        String cardId = clickedCard.getId();
        playButton.setDisable(false);

        cardIndex = Integer.parseInt(cardId.replace("card", ""));

        if (selectedCard != null) {
            selectedCard.setStyle("-fx-border-color: transparent; -fx-border-width: 1; " +
                    "-fx-border-radius: 5; -fx-background-radius: 5;");
        }

        clickedCard.setStyle("-fx-border-color: #00ff0d; -fx-border-width: 4; " +
                "-fx-border-radius: 5; -fx-background-radius: 5;");

        selectedCard = clickedCard;
    }

    /**
     * Manages the turns of the player and the machines.
     * Updates the UI and handles game logic for turns.
     *
     * @param event the ActionEvent triggered when the play button is clicked.
     */
    @FXML
    public void turnManagement(ActionEvent event) {
        if (!game.getTable().getDrawDeck().deckIsEmpty()) {
            if (game.getCurrentPoints() + game.getHumanPlayer().getHandCards()[cardIndex].getCardValue(game.getCurrentPoints()) > 50) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("""
                                Can't play this card!
                                """);
                alert.showAndWait();
                playButton.setDisable(true);
                return;
            }

            playerTurn();

            selectedCard.setStyle("-fx-border-color: transparent; -fx-border-width: 1; " +
                    "-fx-border-radius: 5; -fx-background-radius: 5;");
        } else {
            game.getTable().refillDeck();
        }

        int turnCount = 0;

        for (int i = 0; i < game.getMachinesAmount(); i++) {
            int index = i;
            if (!game.isMachineActive(index)) {
                continue;
            }
            int delay = 1000 * (++turnCount);
            new Thread(() -> {
                try {
                    Thread.sleep(delay);
                    Platform.runLater(() -> game.updateTurnLabel(index + 1));

                    Thread.sleep(500);

                    Platform.runLater(() -> {
                        if (game.isMachineActive(index)) {
                            if (!game.getTable().getDrawDeck().deckIsEmpty()) {
                                machineTurn(index);
                            } else {
                                game.getTable().refillDeck();
                            }
                        }
                    });

                    Thread.sleep(500);

                    Platform.runLater(() -> {
                        if (isLastMachine(index)) {
                            game.updateTurnLabel(0);
                            playButton.setVisible(true);

                            boolean canContinue = game.checkCanPlay(game.getCurrentPoints(), game.getHumanPlayer());
                            if (!canContinue) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information");
                                alert.setHeaderText(null);
                                alert.setContentText("""
                                You lost!
                                """);
                                alert.showAndWait();
                                Stage stage = (Stage) playButton.getScene().getWindow();
                                stage.close();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Checks if the specified machine is the last active machine.
     *
     * @param index the index of the machine to check.
     * @return true if the specified machine is the last active machine, false otherwise.
     */
    public boolean isLastMachine(int index) {
        for (int i = index; i < game.getMachinesAmount(); i++) {
            try {
                if (game.isMachineActive(i + 1)) {
                    return false;
                }
            } catch (IndexOutOfBoundsException e) {
                return true;
            }
        }
        return true;
    }

    /**
     * Initializes the game with the player's name and number of machines.
     * Sets up the initial game state and updates the UI.
     *
     * @param playerName     the name of the player.
     * @param machinesAmount the number of machines in the game.
     */
    public void initialize(String playerName, int machinesAmount) {
        game = new GameModel(playerName, machinesAmount);

        game.addObserver(new TurnObserver(turnsLabel));
        game.addObserver(new PointsObserver(pointsLabel1));

        for (int i = 0; i < game.getMachinesAmount(); i++) {
            revealMachineHand(i);
        }

        Card[] humanCards = game.getHumanCards();

        for (int i = 0; i < humanCards.length; i++) {
            updateCard(i, humanCards[i].getValue(), humanCards[i].getIcon());
        }
        updateCard(4, game.getCurrentCard().getValue(), game.getCurrentCard().getIcon());
        turnsLabel.setText(game.getPlayerName() + "'s turn");
        game.setCurrentPoints(game.getCurrentCard().getCardValue(game.getCurrentPoints()));
        pointsLabel1.setText(String.valueOf(game.getCurrentPoints()));
    }

    /**
     * Executes the player's turn, updating the game state and UI.
     */
    public void playerTurn() {
        game.playCard(cardIndex);
        game.setCurrentCard();
        hidePlayerCard(cardIndex);
        game.setCurrentPoints(game.getCurrentCard().getCardValue(game.getCurrentPoints()));
        game.updatePointsLabel(game.getCurrentPoints());

        updateCard(4, game.getCurrentCard().getValue(), game.getCurrentCard().getIcon());
        playButton.setDisable(false);
        playButton.setVisible(false);

        Timeline timeline = new Timeline();

        KeyFrame step1 = new KeyFrame(Duration.seconds(0.5), e -> {
            showPlayerCard(cardIndex);
            updateCard(cardIndex, game.getHumanCards()[cardIndex].getValue(), game.getHumanCards()[cardIndex].getIcon());
        });

        timeline.getKeyFrames().addAll(step1);
        timeline.play();
    }

    /**
     * Executes the turn for a machine, updating the game state and UI.
     *
     * @param index the index of the machine taking the turn.
     */
    public void machineTurn(int index) {
        int selectedCard = game.playMachine(index);
        if (selectedCard == -1) {
            hideMachineHand(index);
            if (game.getActiveMachineIndexes().size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("""
                                You won!
                                """);
                alert.showAndWait();
                Stage stage = (Stage) playButton.getScene().getWindow();
                stage.close();
            }
        } else {
            hideMachineCard(index, selectedCard);
            game.setCurrentPoints(game.getCurrentCard().getCardValue(game.getCurrentPoints()));
            game.updatePointsLabel(game.getCurrentPoints());

            updateCard(4, game.getCurrentCard().getValue(), game.getCurrentCard().getIcon());
            playButton.setDisable(true);

            Timeline timeline = new Timeline();

            KeyFrame step1 = new KeyFrame(Duration.seconds(0.5), e -> {
                showMachineCard(index, selectedCard);
            });

            timeline.getKeyFrames().addAll(step1);
            timeline.play();
        }
    }

    /**
     * Updates the UI representation of a card with the given details.
     *
     * @param index the index of the card.
     * @param number the card's number.
     * @param icon the card's suit/icon.
     */
    public void updateCard(int index, String number, String icon) {
        AnchorPane card = (AnchorPane) root.lookup("#card" + index);
        if (card != null) {
            Label label1 = (Label) card.lookup("#numberLabel0");
            Label label2 = (Label) card.lookup("#suitLabel");
            Label label3 = (Label) card.lookup("#numberLabel1");
            if (label1 != null) label1.setText(number);
            if (label2 != null) label2.setText(icon);
            if (label3 != null) label3.setText(number);

            String color = (icon.equals("♥") || icon.equals("♦")) ? "red" : "black";

            if (label1 != null) label1.setStyle("-fx-text-fill: " + color + ";");
            if (label2 != null) label2.setStyle("-fx-text-fill: " + color + ";");
            if (label3 != null) label3.setStyle("-fx-text-fill: " + color + ";");
        }
    }

    /**
     * Hides the player's card at the specified index.
     *
     * @param index the index of the card to hide.
     */
    public void hidePlayerCard(int index) {
        AnchorPane card = (AnchorPane) root.lookup("#card" + index);
        if (card != null) {
            card.setVisible(false);
        }
    }

    /**
     * Shows the player's card at the specified index.
     *
     * @param index the index of the card to show.
     */
    public void showPlayerCard(int index) {
        AnchorPane card = (AnchorPane) root.lookup("#card" + index);
        if (card != null) {
            card.setVisible(true);
        }
    }

    /**
     * Hides a machine's card at the specified indexes.
     *
     * @param machineIndex the index of the machine.
     * @param cardIndex    the index of the card to hide.
     */
    public void hideMachineCard(int machineIndex, int cardIndex) {
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null) {
            ImageView card = (ImageView) machineHand.lookup("#machineCard" + cardIndex);
            if (card != null) {
                card.setVisible(false);
            }
        }
    }

    /**
     * Shows a machine's card at the specified indexes.
     *
     * @param machineIndex the index of the machine.
     * @param cardIndex    the index of the card to show.
     */
    public void showMachineCard(int machineIndex, int cardIndex) {
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null) {
            ImageView card = (ImageView) machineHand.lookup("#machineCard" + cardIndex);
            if (card != null) {
                card.setVisible(true);
            }
        }
    }

    /**
     * Reveals the entire hand of a machine.
     *
     * @param machineIndex the index of the machine whose hand will be revealed.
     */
    public void revealMachineHand(int machineIndex) {
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null) {
            machineHand.setVisible(true);
        }
    }

    /**
     * Hides the entire hand of a machine.
     *
     * @param machineIndex the index of the machine whose hand will be hidden.
     */
    public void hideMachineHand(int machineIndex) {
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null) {
            machineHand.setVisible(false);
        }
    }
}