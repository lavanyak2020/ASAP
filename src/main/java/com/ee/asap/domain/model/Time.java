package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;


@AllArgsConstructor
@Getter
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

    public boolean isOlderThanOrSame(Time that) {
        return this.unit == that.unit && this.value <= that.value;
    }

    public Time plus(Time that) {
        return new Time(this.value + that.value, TimeUnit.HOURS);
    }
}

