package com.ee.asap.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Cost {
    private final double value;
    private final Currency currency;


    public Cost minus(Cost that) {
        return new Cost(this.value - that.value, Currency.RUPEE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return Double.compare(cost.value, value) == 0 && Objects.equals(currency, cost.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}
