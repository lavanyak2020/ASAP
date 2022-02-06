package com.ee.asap;

import com.ee.asap.datalayer.OfferRepository;
import com.ee.asap.datalayer.PackageRepository;
import com.ee.asap.datalayer.VehicleRepository;
import com.ee.asap.domain.constants.enums.Currency;
import com.ee.asap.domain.constants.enums.DistanceUnit;
import com.ee.asap.domain.constants.enums.SpeedUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import com.ee.asap.domain.model.Cost;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Package;
import com.ee.asap.domain.model.Speed;
import com.ee.asap.domain.model.Vehicle;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.dto.PackageDto;
import com.ee.asap.service.OfferService;
import com.ee.asap.service.PackageService;
import com.ee.asap.service.VehicleService;
import com.ee.asap.service.ZeroVehiclesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ASAPIntegrationTest {
    private PackageService packageService;

    @BeforeEach
    void setUp() {
        OfferRepository offerRepository = new OfferRepository();
        PackageRepository packageRepository = new PackageRepository();
        OfferService offerService = new OfferService(offerRepository);
        Cost baseDeliveryCost = new Cost(100, Currency.RUPEE);
        VehicleRepository vehicleRepository = new VehicleRepository();
        VehicleService vehicleService = new VehicleService(vehicleRepository);
        vehicleRepository.add(new Vehicle("V1", new Weight(200, WeightUnit.KG), new Speed(70, SpeedUnit.KM_PER_HOUR)));
        vehicleRepository.add(new Vehicle("V2", new Weight(200, WeightUnit.KG), new Speed(70, SpeedUnit.KM_PER_HOUR)));
        packageService = new PackageService(packageRepository, offerService, vehicleService);
        packageService.setBaseDeliveryCost(baseDeliveryCost);
    }

    @Test
    void shouldReturnDiscount0IfAppliedOfferIsInvalid() {
        String invalidOfferId = "OFR001";
        PackageDto packageDto = new PackageDto("PKG1", new Weight(5, WeightUnit.KG), new Distance(5, DistanceUnit.KM), invalidOfferId);

        Package aPackage = packageService.addPackage(packageDto);

        Cost zeroRupees = new Cost(0, Currency.RUPEE);
        Cost oneHundredAndSeventyFiveRupees = new Cost(175, Currency.RUPEE);
        assertThat(aPackage.getDiscount(), is(equalTo(zeroRupees)));
        assertThat(aPackage.getTotalCost(), is(equalTo(oneHundredAndSeventyFiveRupees)));
    }

    @Test
    void shouldTotalCostBe275() {
        String invalidOfferId = "OFR002";
        PackageDto packageDto = new PackageDto("PKG1", new Weight(15, WeightUnit.KG), new Distance(5, DistanceUnit.KM), invalidOfferId);

        Package aPackage = packageService.addPackage(packageDto);

        Cost zeroRupees = new Cost(0, Currency.RUPEE);
        Cost twoHundredAndSeventyFiveRupees = new Cost(275, Currency.RUPEE);
        assertThat(aPackage.getDiscount(), is(equalTo(zeroRupees)));
        assertThat(aPackage.getTotalCost(), is(equalTo(twoHundredAndSeventyFiveRupees)));
    }

    @Test
    void shouldTotalCostBe665AndDiscountBe35() {
        String offer3 = "OFR003";
        PackageDto packageDto = new PackageDto("PKG1", new Weight(10, WeightUnit.KG), new Distance(100, DistanceUnit.KM), offer3);

        Package aPackage = packageService.addPackage(packageDto);

        Cost thirtyFiveRupees = new Cost(35, Currency.RUPEE);
        Cost sixHundredAndSixtyFiveRupees = new Cost(665, Currency.RUPEE);
        assertThat(aPackage.getDiscount(), is(equalTo(thirtyFiveRupees)));
        assertThat(aPackage.getTotalCost(), is(equalTo(sixHundredAndSixtyFiveRupees)));
    }

    @Test
    void shouldDiscountBe0IfAppliedOfferNotFound() {
        String offer3 = "OFR004";
        PackageDto packageDto = new PackageDto("PKG1", new Weight(10, WeightUnit.KG), new Distance(100, DistanceUnit.KM), offer3);

        Package aPackage = packageService.addPackage(packageDto);

        Cost zeroRupees = new Cost(0, Currency.RUPEE);
        Cost sevenHundredRupees = new Cost(700, Currency.RUPEE);
        assertThat(aPackage.getDiscount(), is(equalTo(zeroRupees)));
        assertThat(aPackage.getTotalCost(), is(equalTo(sevenHundredRupees)));
    }

    @Test
    void shouldEstimateCostOfEachPackage() throws ZeroVehiclesException {
        PackageDto packageDto1 = new PackageDto("PKG1", new Weight(50, WeightUnit.KG), new Distance(30, DistanceUnit.KM), "OFR001");
        PackageDto packageDto2 = new PackageDto("PKG2", new Weight(75, WeightUnit.KG), new Distance(125, DistanceUnit.KM), "OFFR0008");
        PackageDto packageDto3 = new PackageDto("PKG3", new Weight(175, WeightUnit.KG), new Distance(100, DistanceUnit.KM), "OFFR003");
        PackageDto packageDto4 = new PackageDto("PKG4", new Weight(110, WeightUnit.KG), new Distance(60, DistanceUnit.KM), "OFFR002");
        PackageDto packageDto5 = new PackageDto("PKG5", new Weight(155, WeightUnit.KG), new Distance(95, DistanceUnit.KM), "NA");
        Package aPackage1 = packageService.addPackage(packageDto1);
        Package aPackage2 = packageService.addPackage(packageDto2);
        Package aPackage3 = packageService.addPackage(packageDto3);
        Package aPackage4 = packageService.addPackage(packageDto4);
        Package aPackage5 = packageService.addPackage(packageDto5);

        packageService.calculateEstimationTimes(new Weight(200, WeightUnit.KG), new Speed(70, SpeedUnit.KM_PER_HOUR));

        assertNotNull(aPackage1.getEstimatedTime());
        assertNotNull(aPackage2.getEstimatedTime());
        assertNotNull(aPackage3.getEstimatedTime());
        assertNotNull(aPackage4.getEstimatedTime());
        assertNotNull(aPackage5.getEstimatedTime());
    }
}
