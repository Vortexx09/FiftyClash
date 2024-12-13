package com.example.fiftyclash.controllers.observers;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class TurnObserver implements Observer{

    private final Label turnsLabel;

    public TurnObserver(Label turnsLabel) {
        this.turnsLabel = turnsLabel;
    }

    @Override
    public void update(String message) {
        if (!message.matches("-?\\d+")) {
            Platform.runLater(() -> turnsLabel.setText(message));
        }
    }

}
