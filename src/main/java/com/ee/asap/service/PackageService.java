package com.ee.asap.service;

import com.ee.asap.datalayer.PackageRepository;
import com.ee.asap.domain.model.Cost;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.domain.model.Package;
import com.ee.asap.domain.model.Speed;
import com.ee.asap.domain.model.Vehicle;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.dto.PackageDto;
import com.ee.asap.exception.OfferNotFoundException;
import com.ee.asap.exception.ZeroVehiclesException;

import java.util.List;

import static com.ee.asap.domain.model.Package.getEfficientComboOfSize;
import static com.ee.asap.domain.model.Package.getShipmentDistanceFor;
import static com.ee.asap.domain.model.Package.packagesToEstimate;
import static com.ee.asap.mapper.PackageMapper.toDomainEntity;

public class PackageService {
    public Cost baseDeliveryCost;
    public final PackageRepository packageRepository;
    public final OfferService offerService;
    public final VehicleService vehicleService;

    public PackageService(PackageRepository packageRepository, OfferService offerService, VehicleService vehicleService) {
        this.packageRepository = packageRepository;
        this.offerService = offerService;
        this.vehicleService = vehicleService;
    }

    public Package addPackage(PackageDto receivedPackage) {
        Package aPackage = toDomainEntity(receivedPackage);
        try {
            Offer appliedOffer = offerService.getById(receivedPackage.getOfferId());
            aPackage.calculateTotalCostWith(baseDeliveryCost, appliedOffer);
        } catch (OfferNotFoundException e) {
            System.out.println(e.getMessage());
            aPackage.calculateTotalCostWith(baseDeliveryCost);
        }
        packageRepository.add(aPackage);
        return aPackage;
    }

    public void setBaseDeliveryCost(Cost baseDeliveryCost) {
        this.baseDeliveryCost = baseDeliveryCost;
    }

    public void calculateEstimationTimes(Weight maxWeight, Speed speed) throws ZeroVehiclesException {
        List<Package> packages = packageRepository.findAll();
        List<Package> packagesToEstimate = packagesToEstimate(packages);

        while (packagesToEstimate.size() > 0) {
            for (int size = packagesToEstimate.size(); size > 0; size--) {
                List<Package> efficientPackageCombo = getEfficientComboOfSize(packagesToEstimate, maxWeight, speed, size);
                if (efficientPackageCombo != null) {
                    Vehicle vehicle = vehicleService.getAnAvailableVehicle();
                    Distance longestDistance = getShipmentDistanceFor(efficientPackageCombo);
                    for (Package aPackage : efficientPackageCombo) {
                        aPackage.addEstimatedTime(vehicle.getAvailableAfter(), speed);
                    }
                    vehicle.updateAvailableTime(longestDistance);
                    break;
                }
            }
            packagesToEstimate = packagesToEstimate(packages);
        }
    }

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }
}

