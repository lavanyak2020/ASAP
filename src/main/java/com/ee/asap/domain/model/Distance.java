package com.ee.asap.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Distance {

    private final float value;
    private final DistanceUnit unit;

    public boolean isGreaterOrEqual(Distance that) {
        return this.unit.equals(that.unit) && this.value >= that.value;
    }

    public boolean isLessOrEqual(Distance that) {
        return this.unit.equals(that.unit) && this.value <= that.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return Float.compare(distance.value, value) == 0 && Objects.equals(unit, distance.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}
