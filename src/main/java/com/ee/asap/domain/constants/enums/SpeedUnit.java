package com.ee.asap.domain.constants.enums;

public class SpeedUnit {
    public static final SpeedUnit KM_PER_HOUR = new SpeedUnit("km/hr");

    public final String name;

    public SpeedUnit(String name) {
        this.name = name;
    }
}
