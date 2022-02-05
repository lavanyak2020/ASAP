package com.ee.asap.domain.constants.enums;

public class DistanceUnit {
    public static final DistanceUnit KM = new DistanceUnit("km");

    public final String name;

    private DistanceUnit(String name) {
        this.name = name;
    }
}

