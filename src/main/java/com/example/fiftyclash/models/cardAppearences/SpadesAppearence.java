package com.example.fiftyclash.models.cardAppearences;

public class SpadesAppearence implements CardAppearence {
    @Override
    public String getIcon() {
        return "♠";
    }

    @Override
    public String getColor() {
        return "BLACK";
    }
}
