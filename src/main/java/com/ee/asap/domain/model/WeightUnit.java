package com.ee.asap.domain.model;

public class WeightUnit {
    public static final WeightUnit KG = new WeightUnit("kg");

    public final String name;

    private WeightUnit(String name) {
        this.name = name;
    }
}
