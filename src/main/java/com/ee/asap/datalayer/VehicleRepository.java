package com.ee.asap.datalayer;

import com.ee.asap.domain.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {
    List<Vehicle> vehicles;

    public VehicleRepository() {
        this.vehicles = new ArrayList<>();
    }

    public void add(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> findAll() {
        return vehicles;
    }
}
