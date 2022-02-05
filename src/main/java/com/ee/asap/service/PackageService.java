package com.ee.asap.service;

import com.ee.asap.datalayer.PackageRepository;
import com.ee.asap.domain.model.Cost;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.domain.model.Package;
import com.ee.asap.dto.PackageDto;
import com.ee.asap.exception.OfferNotFoundException;

import static com.ee.asap.service.PackageMapper.toDomainEntity;

public class PackageService {
    public Cost baseDeliveryCost;
    public final PackageRepository packageRepository;
    public final OfferService offerService;

    public PackageService(PackageRepository packageRepository, OfferService offerService) {
        this.packageRepository = packageRepository;
        this.offerService = offerService;
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
}

