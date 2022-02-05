package com.ee.asap.domain.model;

public class TimeUnit {
    public static final TimeUnit HOURS = new TimeUnit("Hours");

    public final String name;

    private TimeUnit(String name) {
        this.name = name;
    }
}
