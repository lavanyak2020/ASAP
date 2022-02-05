package com.ee.asap.service;

import com.ee.asap.datalayer.PackageRepository;
import com.ee.asap.domain.constants.enums.Currency;
import com.ee.asap.domain.constants.enums.DistanceUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import com.ee.asap.domain.model.Cost;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.domain.model.Package;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteria;
import com.ee.asap.dto.PackageDto;
import com.ee.asap.exception.OfferNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PackageServiceTest {
    private PackageRepository packageRepository;
    private OfferService offerService;
    private PackageService packageService;

    @BeforeEach
    void setUp() {
        packageRepository = mock(PackageRepository.class);
        offerService = mock(OfferService.class);
        packageService = new PackageService(new Cost(100, Currency.RUPEE), packageRepository, offerService);
    }

    @Test
    void shouldReturnPackageDomainObjectWithCost() throws OfferNotFoundException {
        String offerId = "offer-id";
        PackageDto aPackage = new PackageDto("PKG1", new Weight(10, WeightUnit.KG), new Distance(50, DistanceUnit.KM), offerId);
        DistanceAndWeightCriteria criteria = DistanceAndWeightCriteria.builder()
                                                                      .minDistance(new Distance(1, DistanceUnit.KM))
                                                                      .maxDistance(new Distance(199, DistanceUnit.KM))
                                                                      .minWeight(new Weight(70, WeightUnit.KG))
                                                                      .maxWeight(new Weight(200, WeightUnit.KG))
                                                                      .build();
        Offer testOffer = Offer.builder().id(offerId).discountPercentage(10).criteria(criteria).build();
        when(offerService.getById(offerId)).thenReturn(testOffer);

        Package packageEntity = packageService.addPackage(aPackage);

        verify(packageRepository, times(1)).add(Mockito.any());
        assertNotNull(packageEntity.getDiscount());
        assertNotNull(packageEntity.getTotalCost());
    }

    @Test
    void shouldThrowExceptionIfOfferNotFound() throws OfferNotFoundException {
        String offerId = "offer-id";
        PackageDto aPackage = new PackageDto("PKG1", new Weight(10, WeightUnit.KG), new Distance(50, DistanceUnit.KM), offerId);
        when(offerService.getById(offerId)).thenThrow(new OfferNotFoundException("Offer not found"));

        assertThrows(OfferNotFoundException.class, () -> packageService.addPackage(aPackage));
    }
}