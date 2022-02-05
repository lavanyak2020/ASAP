package com.ee.asap.service;

import com.ee.asap.datalayer.PackageRepository;
import com.ee.asap.domain.model.Cost;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.domain.model.Package;
import com.ee.asap.dto.PackageDto;
import com.ee.asap.exception.OfferNotFoundException;

public class PackageService {
    public final Cost baseDeliveryCost;
    public final PackageRepository packageRepository;
    public final OfferService offerService;

    public PackageService(Cost baseDeliveryCost, PackageRepository packageRepository, OfferService offerService) {
        this.baseDeliveryCost = baseDeliveryCost;
        this.packageRepository = packageRepository;
        this.offerService = offerService;
    }

    public Package addPackage(PackageDto receivedPackage) throws OfferNotFoundException {
        Package aPackage = toDomainEntity(receivedPackage);
        aPackage.calculateTotalCostWith(baseDeliveryCost);
        packageRepository.add(aPackage);
        return aPackage;
    }

    private Package toDomainEntity(PackageDto receivedPackage) throws OfferNotFoundException {
        Offer offer = offerService.getById(receivedPackage.getOfferId());
        return new Package("PKG_1", receivedPackage.getWeight(), receivedPackage.getDistanceToDestination(), offer);
    }
}
