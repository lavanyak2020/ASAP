package com.ee.asap.service;

import com.ee.asap.datalayer.VehicleRepository;
import com.ee.asap.domain.constants.enums.SpeedUnit;
import com.ee.asap.domain.constants.enums.TimeUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import com.ee.asap.domain.model.Speed;
import com.ee.asap.domain.model.Time;
import com.ee.asap.domain.model.Vehicle;
import com.ee.asap.domain.model.Weight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehicleServiceTest {
    private VehicleRepository vehicleRepository;
    private VehicleService vehicleService;

    @BeforeEach
    public void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleService = new VehicleService(vehicleRepository);
    }

    @Test
    void shouldReturnVehicleWhichCanBeAvailableFirst() throws ZeroVehiclesException {
        Vehicle vehicle1 = new Vehicle("1", new Weight(200, WeightUnit.KG), new Speed(60, SpeedUnit.KM_PER_HOUR));
        vehicle1.addAvailableTimeBy(new Time(1, TimeUnit.HOURS));
        Vehicle vehicle2 = new Vehicle("2", new Weight(200, WeightUnit.KG), new Speed(60, SpeedUnit.KM_PER_HOUR));
        vehicle2.addAvailableTimeBy(new Time(2, TimeUnit.HOURS));
        Vehicle vehicle3 = new Vehicle("3", new Weight(200, WeightUnit.KG), new Speed(60, SpeedUnit.KM_PER_HOUR));
        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle1, vehicle2, vehicle3));

        Vehicle actualVehicle = vehicleService.getAnAvailableVehicle();

        assertThat(actualVehicle, is(equalTo(vehicle3)));
    }

    @Test
    void shouldReturnFirstVehicleWhenMultipleVehiclesAreAvailable() throws ZeroVehiclesException {
        Vehicle vehicle1 = new Vehicle("1", new Weight(200, WeightUnit.KG), new Speed(60, SpeedUnit.KM_PER_HOUR));
        vehicle1.addAvailableTimeBy(new Time(1, TimeUnit.HOURS));
        Vehicle vehicle2 = new Vehicle("2", new Weight(200, WeightUnit.KG), new Speed(60, SpeedUnit.KM_PER_HOUR));
        Vehicle vehicle3 = new Vehicle("3", new Weight(200, WeightUnit.KG), new Speed(60, SpeedUnit.KM_PER_HOUR));
        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle1, vehicle2, vehicle3));

        Vehicle actualVehicle = vehicleService.getAnAvailableVehicle();

        assertThat(actualVehicle, is(equalTo(vehicle2)));
    }

    @Test
    void shouldThrowExceptionWhenThereIsZeroVehicles() {
        when(vehicleRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ZeroVehiclesException.class, vehicleService::getAnAvailableVehicle);
    }
}