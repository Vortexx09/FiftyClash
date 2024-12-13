package com.example.fiftyclash.controllers.observers;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class PointsObserver implements Observer {

    private final Label pointsLabel;

    public PointsObserver(Label pointsLabel) {
        this.pointsLabel = pointsLabel;
    }

    @Override
    public void update(String message) {
        if (message.matches("-?\\d+")) {
            Platform.runLater(() -> pointsLabel.setText(message));
        }
    }

}
