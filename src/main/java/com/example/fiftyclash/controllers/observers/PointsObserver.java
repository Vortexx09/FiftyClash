package com.example.fiftyclash.controllers.observers;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Implements the {@link Observer} interface to update a {@link Label} with point-related messages.
 * It updates the label on the JavaFX Application Thread using {@link Platform#runLater(Runnable)}.
 */
public class PointsObserver implements Observer {

    /** The label to display the points information. */
    private final Label pointsLabel;

    /**
     * Constructs a new PointsObserver with the specified label.
     *
     * @param pointsLabel the {@link Label} to update with point messages.
     */
    public PointsObserver(Label pointsLabel) {
        this.pointsLabel = pointsLabel;
    }

    /**
     * Updates the label with the provided message, ensuring thread safety by using
     * {@link Platform#runLater(Runnable)} to modify the label on the JavaFX Application Thread.
     * The label is only updated if the message is a numeric value.
     *
     * @param message the message to display on the label.
     */
    @Override
    public void update(String message) {
        if (message.matches("-?\\d+")) {
            Platform.runLater(() -> pointsLabel.setText(message));
        }
    }
}