package com.example.fiftyclash.controllers;

import com.example.fiftyclash.models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class GameController {
    @FXML
    private AnchorPane root, machineHand0, machineHand1, machineHand2;

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
    }

    @FXML
    public void turnManagement(ActionEvent event) {
        playerTurn();

        for (int i = 0; i < game.getMachinesAmount(); i++) {
            int index = i;

            new Thread(() -> {
                try {
                    Thread.sleep(1000 * (index + 1));
                    Platform.runLater(() -> machineTurn(index));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void initialize(String playerName, int machinesAmount){
        game = new GameModel(playerName, machinesAmount);

        for (int i = 0; i < game.getMachinesAmount(); i++) {
            revealMachineHand(i);
        }

        Card[] humanCards = game.getHumanCards();

        for (int i = 0; i < humanCards.length; i++){
            updateCard(i, humanCards[i].getValue(), humanCards[i].getIcon());
        }
        updateCard(4, game.getCurrentCard().getValue(), game.getCurrentCard().getIcon());
        pointsLabel1.setText(String.valueOf(game.getCurrentPoints()));
    }

    public void playerTurn(){
        game.playCard(cardIndex);
        game.setCurrentCard();
        hidePlayerCard(cardIndex);

        pointsLabel1.setText(String.valueOf(game.getCurrentPoints()));

        updateCard(4, game.getCurrentCard().getValue(), game.getCurrentCard().getIcon());
        playButton.setDisable(false);

        Timeline timeline = new Timeline();

        KeyFrame step1 = new KeyFrame(Duration.seconds(0.5), e -> {
            showPlayerCard(cardIndex);
            updateCard(cardIndex, game.getHumanCards()[cardIndex].getValue(), game.getHumanCards()[cardIndex].getIcon());
        });

        timeline.getKeyFrames().addAll(step1);
        timeline.play();
        turnsLabel.setText("Machine1's turn");
    }

    public void machineTurn(int index){
        int selectedCard = game.playMachine(index);
        hideMachineCard(index, selectedCard);
        pointsLabel1.setText(String.valueOf(game.getCurrentPoints()));


        updateCard(4, game.getCurrentCard().getValue(), game.getCurrentCard().getIcon());
        playButton.setDisable(true);

        Timeline timeline = new Timeline();

        KeyFrame step1 = new KeyFrame(Duration.seconds(0.5), e -> {
            showMachineCard(index, selectedCard);
        });

        timeline.getKeyFrames().addAll(step1);
        timeline.play();
        turnsLabel.setText((index + 2) <= game.getMachinesAmount() ? "Machine" + (index + 2) + "'s turn"  : game.getPlayerName() + "'s turn");
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
            else {
                System.out.println("NO CARD FOUND");
            }
        }
        else{
            System.out.println("NO MACHINE HAND FOUND");
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
}