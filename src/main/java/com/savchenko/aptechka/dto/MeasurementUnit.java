package com.savchenko.aptechka.dto;

public enum MeasurementUnit {
    PACKAGE,
    BLISTER,
    AMPULE,
    ML,
    BOTTLE,
    PIECE;

    public String getDisplayName() {
        String lower = name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}