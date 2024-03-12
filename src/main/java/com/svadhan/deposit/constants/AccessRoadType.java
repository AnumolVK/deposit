package com.svadhan.deposit.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessRoadType {
    NO_ROAD_ACCESS("No road access"),
    TWO_WHEELER("Two wheeler"),
    FOUR_WHEELER("Four wheeler");
    private final String value;

    AccessRoadType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
