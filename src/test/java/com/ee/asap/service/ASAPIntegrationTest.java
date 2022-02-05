package com.ee.asap.service;

import com.ee.asap.datalayer.OfferRepository;
import com.ee.asap.datalayer.PackageRepository;
import com.ee.asap.domain.constants.enums.Currency;
import com.ee.asap.domain.constants.enums.DistanceUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import com.ee.asap.domain.model.Cost;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Package;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.dto.PackageDto;
import com.ee.asap.exception.OfferNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ASAPIntegrationTest {
    private PackageService packageService;

    @BeforeEach
    void setUp() {
        OfferRepository offerRepository = new OfferRepository();
        PackageRepository packageRepository = new PackageRepository();
        OfferService offerService = new OfferService(offerRepository);
        Cost baseDeliveryCost = new Cost(100, Currency.RUPEE);
        packageService = new PackageService(baseDeliveryCost, packageRepository, offerService);
    }

    @Test
    void shouldReturnDiscount0IfAppliedOfferIsInvalid() throws OfferNotFoundException {
        String invalidOfferId = "OFR001";
        PackageDto packageDto = new PackageDto("PKG1", new Weight(5, WeightUnit.KG), new Distance(5, DistanceUnit.KM), invalidOfferId);

        Package aPackage = packageService.addPackage(packageDto);

        Cost zeroRupees = new Cost(0, Currency.RUPEE);
        Cost oneHundredAndSeventyFiveRupees = new Cost(175, Currency.RUPEE);
        assertThat(aPackage.getDiscount(), is(equalTo(zeroRupees)));
        assertThat(aPackage.getTotalCost(), is(equalTo(oneHundredAndSeventyFiveRupees)));
    }

    @Test
    void shouldTotalCostBe275() throws OfferNotFoundException {
        String invalidOfferId = "OFR002";
        PackageDto packageDto = new PackageDto("PKG1", new Weight(15, WeightUnit.KG), new Distance(5, DistanceUnit.KM), invalidOfferId);

        Package aPackage = packageService.addPackage(packageDto);

        Cost zeroRupees = new Cost(0, Currency.RUPEE);
        Cost twoHundredAndSeventyFiveRupees = new Cost(275, Currency.RUPEE);
        assertThat(aPackage.getDiscount(), is(equalTo(zeroRupees)));
        assertThat(aPackage.getTotalCost(), is(equalTo(twoHundredAndSeventyFiveRupees)));
    }

    @Test
    void shouldTotalCostBe665AndDiscountBe35() throws OfferNotFoundException {
        String offer3 = "OFR003";
        PackageDto packageDto = new PackageDto("PKG1", new Weight(10, WeightUnit.KG), new Distance(100, DistanceUnit.KM), offer3);

        Package aPackage = packageService.addPackage(packageDto);

        Cost thirtyFiveRupees = new Cost(35, Currency.RUPEE);
        Cost sixHundredAndSixtyFiveRupees = new Cost(665, Currency.RUPEE);
        assertThat(aPackage.getDiscount(), is(equalTo(thirtyFiveRupees)));
        assertThat(aPackage.getTotalCost(), is(equalTo(sixHundredAndSixtyFiveRupees)));
    }
}
