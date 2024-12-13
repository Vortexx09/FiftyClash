package com.example.fiftyclash.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testGetCardValueWithNumber() {
        Card eight = new Card("8", "clubs");

        int expectedValue = 8;

        assertEquals(expectedValue, eight.getCardValue());
    }

    @Test
    void testGetCardValueWithLetter() {
        Card king = new Card("K", "hearts");

        int expectedValue = -10;

        assertEquals(expectedValue, king.getCardValue());
    }

    @Test
    void testGetCardValueWithNine() {
        Card nine = new Card("9", "diamonds");

        int expectedValue = 0;

        assertEquals(expectedValue, nine.getCardValue());
    }

}