package com.example.fiftyclash.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testGetCardValueWithNumber() {
        Card eight = new Card("8", "clubs");
        int currentPoints = 10;

        int expectedValue = 8;

        assertEquals(expectedValue, eight.getCardValue(currentPoints));
    }

    @Test
    void testGetCardValueWithLetter() {
        Card king = new Card("K", "hearts");
        int currentPoints = 10;

        int expectedValue = -10;

        assertEquals(expectedValue, king.getCardValue(currentPoints));
    }

    @Test
    void testGetCardValueWithNine() {
        Card nine = new Card("9", "diamonds");
        int currentPoints = 10;


        int expectedValue = 0;

        assertEquals(expectedValue, nine.getCardValue(currentPoints));
    }

}