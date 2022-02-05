package com.ee.asap.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Weight {

    private final double value;
    private final WeightUnit unit;

    public boolean isGreaterOrEqual(Weight that) {
        return this.unit.equals(that.unit) && this.value >= that.value;
    }

    public boolean isLessOrEqual(Weight that) {
        return this.unit.equals(that.unit) && this.value <= that.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return Double.compare(weight.value, value) == 0 && Objects.equals(unit, weight.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}
