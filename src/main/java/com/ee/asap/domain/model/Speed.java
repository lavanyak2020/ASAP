package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.SpeedUnit;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Speed {
    private final double value;
    private final SpeedUnit speedUnit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speed speed = (Speed) o;
        return Double.compare(speed.value, value) == 0 && Objects.equals(speedUnit, speed.speedUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, speedUnit);
    }
}
