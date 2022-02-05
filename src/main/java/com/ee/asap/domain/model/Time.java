package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.TimeUnit;
import lombok.AllArgsConstructor;

import java.util.Objects;


@AllArgsConstructor
public class Time {
    private final double value;
    private final TimeUnit unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return Double.compare(time.value, value) == 0 && Objects.equals(unit, time.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}

