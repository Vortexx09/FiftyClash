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

public class GameController {
    @FXML
    private AnchorPane root;
    private AnchorPane selectedCard;

    @FXML
    private Label pointsLabel1, turnsLabel;

    @FXML
    private Button playButton, startButton;

    int cardIndex;

    GameModel game;


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

    @FXML
    public void turnManagement(ActionEvent event) {

        if (!game.getTable().getDrawDeck().deckIsEmpty()){
            if (game.getCurrentPoints() + game.getHumanPlayer().getHandCards()[cardIndex].getCardValue(game.getCurrentPoints()) > 50){

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

        }
        else{
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
                            if (!game.getTable().getDrawDeck().deckIsEmpty()){
                                machineTurn(index);
                            }
                            else{
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
                            if (!canContinue){
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

    public void initialize(String playerName, int machinesAmount){
        game = new GameModel(playerName, machinesAmount);

        game.addObserver(new TurnObserver(turnsLabel));
        game.addObserver(new PointsObserver(pointsLabel1));

        for (int i = 0; i < game.getMachinesAmount(); i++) {
            revealMachineHand(i);
        }

        Card[] humanCards = game.getHumanCards();

        for (int i = 0; i < humanCards.length; i++){
            updateCard(i, humanCards[i].getValue(), humanCards[i].getIcon());
        }
        updateCard(4, game.getCurrentCard().getValue(), game.getCurrentCard().getIcon());
        turnsLabel.setText(game.getPlayerName() + "'s turn");
        game.setCurrentPoints(game.getCurrentCard().getCardValue(game.getCurrentPoints()));
        pointsLabel1.setText(String.valueOf(game.getCurrentPoints()));
    }

    public void playerTurn(){
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

    public void machineTurn(int index){
        int selectedCard = game.playMachine(index);
        if (selectedCard == -1) {
            hideMachineHand(index);
            if (game.getActiveMachineIndexes().size() == 0){
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

    public void updateCard(int index, String number, String icon){
        AnchorPane card = (AnchorPane) root.lookup("#card" + index);
        if (card != null) {
            Label label1 = (Label) card.lookup("#numberLabel0");
            Label label2 = (Label) card.lookup("#suitLabel");
            Label label3 = (Label) card.lookup("#numberLabel1");
            if (label1 != null) label1.setText(number);
            if (label2 != null) label2.setText(icon);
            if (label3 != null) label3.setText(number);
        }
    }

    public void hidePlayerCard(int index){
        AnchorPane card = (AnchorPane) root.lookup("#card" + index);
        if (card != null) {
            card.setVisible(false);
        }
    }

    public void showPlayerCard(int index){
        AnchorPane card = (AnchorPane) root.lookup("#card" + index);
        if (card != null) {
            card.setVisible(true);
        }
    }

    public void hideMachineCard(int machineIndex, int cardIndex){
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null) {
            ImageView card = (ImageView) machineHand.lookup("#machineCard" + cardIndex);
            if (card != null){
                card.setVisible(false);
            }
        }
    }

    public void showMachineCard(int machineIndex, int cardIndex){
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null) {
            ImageView card = (ImageView) machineHand.lookup("#machineCard" + cardIndex);
            if (card != null){
                card.setVisible(true);
            }
        }
    }

    public void revealMachineHand(int machineIndex){
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null){
            machineHand.setVisible(true);
        }
    }

    public void hideMachineHand(int machineIndex){
        AnchorPane machineHand = (AnchorPane) root.lookup("#machineHand" + machineIndex);
        if (machineHand != null){
            machineHand.setVisible(false);
        }
    }
}