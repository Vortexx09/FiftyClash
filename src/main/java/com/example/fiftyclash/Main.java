package com.example.fiftyclash;

import com.example.fiftyclash.models.*;
import com.example.fiftyclash.views.GameView;
import com.example.fiftyclash.views.HelloView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HelloView.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
}