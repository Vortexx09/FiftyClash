package com.example.fiftyclash.models;

public class Machine extends Player{
    private Card[] handCards;
    private boolean canPlay;
    private boolean isMachineTurn;

    public Machine() {
        handCards = getHandCards();
    }

    public int selectPlayCard(Deck playDeck){
        int number = handCards[0].getCardValue();
        int points = playDeck.getCurrentPoints();
        int minCardIndex = 0;
        int maxCardIndex = 0;

        if (points > 40){
            for (int i = 0; i < handCards.length; i++) {
                if (handCards[i].getCardValue() < number) {
                    minCardIndex = i;
                }
            }
            return minCardIndex;
        }
        else if (points < 40){
            for (int i = 0; i < handCards.length; i++) {
                if (handCards[i].getCardValue() > number) {
                    maxCardIndex = i;
                }
            }
            return maxCardIndex;
        }
        return minCardIndex;
    }
}
