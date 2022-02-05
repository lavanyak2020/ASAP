package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.TimeUnit;
import lombok.Getter;

@Getter
public class Vehicle {
    private final String id;
    private final Weight maxCarriableWeight;
    private final Speed maxSpeed;
    private Time availableAfter;

    public Vehicle(String id, Weight maxCarriableWeight, Speed maxSpeed) {
        this.id = id;
        this.maxCarriableWeight = maxCarriableWeight;
        this.maxSpeed = maxSpeed;
        this.availableAfter = new Time(0, TimeUnit.HOURS);
    }

    public boolean isAvailableAt(Time time) {
        return availableAfter.isOlderThanOrSame(time);
    }

    public void addAvailableTimeBy(Time time) {
        availableAfter = availableAfter.plus(time);
    }
}
