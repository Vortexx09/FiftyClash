package com.example.fiftyclash.views;

import com.example.fiftyclash.controllers.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Stage {
    private final GameController gameController;
    /**
     * Initializes the GameView by loading the FXML layout and setting
     * the window's title, scene, and other properties.
     *
     * @throws IOException if there is an issue loading the FXML file
     */
    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fiftyclash/game-view.fxml"));
        Parent root = loader.load();
        this.setTitle("Fifty Clash");
        this.gameController = loader.getController();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }

    /**
     * Holds the single instance of GameView, following the Singleton pattern.
     */
    private static class GameViewHolder {
        private static GameView INSTANCE;
    }

    /**
     * Provides access to the single instance of GameView.
     * If the instance does not exist, it is created; otherwise, the existing
     * instance is returned.
     *
     * @return the singleton instance of GameView
     * @throws IOException if there is an issue loading the FXML file
     */
    public static GameView getInstance() throws IOException {

        if (GameViewHolder.INSTANCE == null) {
            return GameViewHolder.INSTANCE = new GameView();
        } else {
            return GameViewHolder.INSTANCE;
        }
    }

    public GameController getGameController() {
        return gameController;
    }

}
