package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.SpeedUnit;
import com.ee.asap.domain.constants.enums.TimeUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void shouldVehicleBeAvailableAtFirstTime() {
        Vehicle vehicle = new Vehicle("VEH_1", new Weight(200, WeightUnit.KG), new Speed(50, SpeedUnit.KM_PER_HOUR));

        assertTrue(vehicle.isAvailableAt(new Time(1, TimeUnit.HOURS)));
    }

    @Test
    void shouldVehicleBeAvailableIfGivenTimeIsNewerThanItsAvailableTime() {
        Vehicle vehicle = new Vehicle("VEH_1", new Weight(200, WeightUnit.KG), new Speed(50, SpeedUnit.KM_PER_HOUR));
        vehicle.addAvailableTimeBy(new Time(1, TimeUnit.HOURS));

        assertTrue(vehicle.isAvailableAt(new Time(1.5, TimeUnit.HOURS)));
    }

    @Test
    void shouldVehicleBeAvailableIfGivenTimeIsSameAsItsAvailableTime() {
        Vehicle vehicle = new Vehicle("VEH_1", new Weight(200, WeightUnit.KG), new Speed(50, SpeedUnit.KM_PER_HOUR));
        vehicle.addAvailableTimeBy(new Time(1, TimeUnit.HOURS));

        assertTrue(vehicle.isAvailableAt(new Time(1, TimeUnit.HOURS)));
    }

    @Test
    void shouldVehicleNotBeAvailableIfGivenTimeIsOlderThanItsAvailableTime() {
        Vehicle vehicle = new Vehicle("VEH_1", new Weight(200, WeightUnit.KG), new Speed(50, SpeedUnit.KM_PER_HOUR));
        vehicle.addAvailableTimeBy(new Time(1, TimeUnit.HOURS));

        assertFalse(vehicle.isAvailableAt(new Time(0.5, TimeUnit.HOURS)));
    }
}