package com.example.fiftyclash.models;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
    }

    public void initializeDeck() {
        String[] suits = {"diamonds", "hearts", "clubs", "spades"};
        String[] value = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < value.length; j++) {
                deck.add(new Card(value[j], suits[i]));
            }
        }
        Collections.shuffle(deck);
    }

    public int getCurrentPoints() {
        int pointsAccumulator = 0;
        if (deck != null) {
            for (Card card : deck) {
                switch (card.getValue()) {
                    case "A":
                        if (pointsAccumulator + card.getCardValue() > 50) pointsAccumulator += 1;
                        else pointsAccumulator += card.getCardValue();
                        break;
                    case "9":
                        pointsAccumulator += 0;
                        break;
                    default:
                        pointsAccumulator += card.getCardValue();
                        break;
                }
            }
        }
        return pointsAccumulator;
    }

    public void printDeck () {
        String icon = "";
        for (int i = 0; i < deck.size(); ++i) {
            icon = deck.get(i).getIcon();
            System.out.println(deck.get(i).getValue() + icon);
        }
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public void removeCard(int index) {
        deck.remove(index);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public int getSize() {
        return deck.size();
    }
}
