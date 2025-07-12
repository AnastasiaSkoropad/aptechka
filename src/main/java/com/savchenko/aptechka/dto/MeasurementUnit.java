package com.savchenko.aptechka.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MeasurementUnit {
    PACKAGE("Пакунок"),
    BLISTER("Блістер"),
    AMPULE("Ампула"),
    ML("мл"),
    BOTTLE("Флакон"),
    PIECE("Штука");

    private final String ukName;

    MeasurementUnit(String ukName) {
        this.ukName = ukName;
    }

    @JsonValue
    public String getDisplayName() {
        return ukName;
    }

    @JsonCreator
    public static MeasurementUnit fromString(String value) {
        if (value == null) {
            return null;
        }
        String v = value.trim();
        for (MeasurementUnit u : values()) {
            if (u.ukName.equalsIgnoreCase(v)) {
                return u;
            }
        }
        try {
            return MeasurementUnit.valueOf(v.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown MeasurementUnit: '" + value + "'. " +
                            "Очікуються англійські коди " +
                            Arrays.toString(values()) +
                            " або українські назви."
            );
        }
    }
}
