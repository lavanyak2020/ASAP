package com.ee.asap.service;

import com.ee.asap.datalayer.VehicleRepository;
import com.ee.asap.domain.model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle getAnAvailableVehicle() throws ZeroVehiclesException {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        if(vehicles.size() == 0 ) {
            throw new ZeroVehiclesException("There no vehicles in the system at the moment");
        }

        ArrayList<Vehicle> sortedVehicles = new ArrayList<>(vehicles);
        sortedVehicles.sort(Comparator.comparingDouble(o -> o.getAvailableAfter().getValue()));
        return sortedVehicles.get(0);
    }

    public Vehicle add(Vehicle vehicle) {
        vehicleRepository.add(vehicle);
        return vehicle;
    }
}

