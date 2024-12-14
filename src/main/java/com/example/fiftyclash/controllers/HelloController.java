package com.example.fiftyclash.controllers;

import com.example.fiftyclash.views.GameView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ToggleGroup machinesNumber;

    @FXML
    private TextField inputUsername;

    int machinesAmount;

    /**
     * Handles the Play button action.
     * Validates the username input and initializes the game view.
     * @param event the event triggered by clicking the Play button.
     * @throws IOException if there is an issue loading the game view.
     */
    @FXML
    protected void startGame(Event event) throws IOException {
        if (inputUsername == null || inputUsername.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your username!");
            alert.showAndWait();
        } else {
            Toggle selectedToggle = machinesNumber.getSelectedToggle();
            if (selectedToggle != null) {
                RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                machinesAmount = Integer.parseInt(selectedRadioButton.getText());
            } else {
                System.out.println("No se seleccionó ninguna opción");
            }

            Node source = (Node) event.getSource();
            Stage actualStage = (Stage) source.getScene().getWindow();
            actualStage.close();

            GameView gameView = GameView.getInstance();
            gameView.show();
            GameController gameController = gameView.getGameController();
            gameController.initialize(inputUsername.getText(), machinesAmount);
        }
    }
    /**
     * Displays game instructions in an informational alert dialog.
     * The instructions include details about the game objective, boards, and gameplay.
     */
    public void onActionButtonInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(""" 
            In "Fifty Clash", the goal is to be the last player remaining in the game. The game is played against 1, 2, or 3 AI players. Each player has 4 cards, and on their turn, they must select a card from their hand that modifies the sum on the table without exceeding 50.

            Main Rules:
            - The sum on the table must not exceed 50.
            - On each turn, the player selects a card from their hand, which can either add or subtract from the table sum.
            - Cards numbered 2 through 8 and 10 add their value to the sum.
            - Cards numbered 9 have no effect on the sum.
            - Cards J, Q, K subtract 10 from the sum.
            - The Ace (A) can either add 1 or 10, depending on what benefits the player.

            Setup:
            - 4 cards are dealt to each player, and one card is placed face-up on the table to start the sum.
            - The remaining cards are kept in the deck.

            End of the Game:
            - The game ends when only one player remains who has not been eliminated for exceeding 50 on the table.

            Other Considerations:
            - If the deck runs out of cards, the cards from the table (except the last played card) should be shuffled and returned to the deck.
            - The cards from eliminated players are added to the end of the deck.
            """);
        alert.showAndWait();
    }
}