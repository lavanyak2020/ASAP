package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CostTest {

    @Test
    void shouldSubtract() {
        Cost tenRupees = new Cost(10, Currency.RUPEE);
        Cost fiftyRupees = new Cost(50, Currency.RUPEE);
        Cost fortyRupees = new Cost(40, Currency.RUPEE);

        assertEquals(fiftyRupees.minus(tenRupees), fortyRupees);
    }
}