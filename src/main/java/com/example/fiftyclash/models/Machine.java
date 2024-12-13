package com.example.fiftyclash.models;

public class Machine extends Player {

    public Machine() {
        handCards = getHandCards();
    }

    @Override
    public int selectPlayCard(int currentPoints) {
        int points = currentPoints;
        int selectedCardIndex = -1;
        int closestTo50 = Integer.MAX_VALUE;

        for (int i = 0; i < handCards.length; i++) {
            int cardValue = handCards[i].getCardValue();

            if (points + cardValue <= 50) {
                int distanceTo50 = 50 - (points + cardValue);

                if (distanceTo50 < closestTo50) {
                    closestTo50 = distanceTo50;
                    selectedCardIndex = i;
                }
            }
        }

        if (selectedCardIndex == -1) {
            System.out.println("No valid card to play, player loses.");
        }

        return selectedCardIndex;
    }
}
