package com.ee.asap.domain.constants.enums;

public class Currency {
    public static final Currency RUPEE = new Currency("Rupees");

    public final String name;
    private Currency(String name) {
        this.name = name;
    }
}
