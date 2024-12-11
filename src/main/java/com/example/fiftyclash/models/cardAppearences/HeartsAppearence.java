package com.example.fiftyclash.models.cardAppearences;

public class HeartsAppearence implements CardAppearence {
    @Override
    public String getIcon() {
        return "♥";
    }

    @Override
    public String getColor() {
        return "RED";
    }

}
