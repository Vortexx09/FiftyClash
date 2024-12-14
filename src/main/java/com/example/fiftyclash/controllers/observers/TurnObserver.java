package com.example.fiftyclash.controllers.observers;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Implements the {@link Observer} interface to update a {@link Label} with turn-related messages.
 * It specifically updates the label on the JavaFX Application Thread using {@link Platform#runLater(Runnable)}.
 */
public class TurnObserver implements Observer {

    /** The label to display the turn information. */
    private final Label turnsLabel;

    /**
     * Constructs a new TurnObserver with the specified label.
     *
     * @param turnsLabel the {@link Label} to update with turn messages.
     */
    public TurnObserver(Label turnsLabel) {
        this.turnsLabel = turnsLabel;
    }

    /**
     * Updates the label with the provided message, ensuring thread safety by using
     * {@link Platform#runLater(Runnable)} to modify the label on the JavaFX Application Thread.
     * The label is only updated if the message is not a numeric value.
     *
     * @param message the message to display on the label.
     */
    @Override
    public void update(String message) {
        if (!message.matches("-?\\d+")) {
            Platform.runLater(() -> turnsLabel.setText(message));
        }
    }
}