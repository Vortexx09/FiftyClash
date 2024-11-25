package com.example.fiftyclash;

import com.example.fiftyclash.views.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameView.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /*
    Deck drawDeck = new Deck();
    Player[] players = new Player[1];
    players[0] = new Player();

    drawDeck.initializeDeck();
    Table mainTable = new Table(drawDeck, players);

    mainTable.getDrawDeck().printDeck();
    mainTable.initializeTable(players);

    System.out.println("AFTER CARD DEAL");
    mainTable.getDrawDeck().printDeck();

    System.out.println("CURRENT CARD");
    System.out.println(mainTable.getCurrentCard().getValue() + " " + mainTable.getCurrentCard().getSuit());

    System.out.println("PLAYER HAND");
    players[0].printHandCards();

    System.out.println("AFTER CARD PLAYED");
    players[0].playCard(mainTable.getPlayDeck(), 0);
    mainTable.updateCurrentCard();
    mainTable.getPlayDeck().printDeck();
    System.out.println("CURRENT POINTS: " + mainTable.getPlayDeck().getCurrentPoints());
    System.out.println("CURRENT CARD: " + mainTable.getCurrentCard().getValue() + " " + mainTable.getCurrentCard().getSuit());

    System.out.println("PLAYER HAND");
    players[0].drawCard(drawDeck);
    players[0].printHandCards();

    System.out.println("AFTER CARD PLAYED N°2");
    players[0].playCard(mainTable.getPlayDeck(), 0);
    mainTable.updateCurrentCard();
    mainTable.getPlayDeck().printDeck();
    System.out.println("CURRENT POINTS: " + mainTable.getPlayDeck().getCurrentPoints());
    System.out.println("CURRENT CARD: " + mainTable.getCurrentCard().getValue() + " " + mainTable.getCurrentCard().getSuit());

    System.out.println("PLAYER HAND N°2");
    players[0].drawCard(drawDeck);
    players[0].printHandCards();

    System.out.println("AFTER CARD PLAYED N°3");
    players[0].playCard(mainTable.getPlayDeck(), 0);
    mainTable.updateCurrentCard();
    mainTable.getPlayDeck().printDeck();
    System.out.println("CURRENT POINTS: " + mainTable.getPlayDeck().getCurrentPoints());
    System.out.println("CURRENT CARD: " + mainTable.getCurrentCard().getValue() + " " + mainTable.getCurrentCard().getSuit());

    System.out.println("PLAYER HAND N°3");
    players[0].drawCard(drawDeck);
    players[0].printHandCards();
     */
}