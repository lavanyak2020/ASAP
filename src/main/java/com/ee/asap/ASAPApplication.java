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
import com.ee.asap.domain.model.Time;
import com.ee.asap.domain.model.Vehicle;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.dto.PackageDto;
import com.ee.asap.service.OfferService;
import com.ee.asap.service.PackageService;
import com.ee.asap.service.VehicleService;
import com.ee.asap.exception.NoVehiclesException;

import java.util.List;
import java.util.Scanner;

public class ASAPApplication {
    private PackageService packageService;
    private VehicleService vehicleService;

    public ASAPApplication() {
        setUp();
    }

    public static void main(String[] args) throws NoVehiclesException {
        ASAPApplication app = new ASAPApplication();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter Base delivery cost: ");
        double baseDeliveryCostValue = scanner.nextDouble();
        Cost baseDeliveryCost = new Cost(baseDeliveryCostValue, Currency.RUPEE);
        app.packageService.setBaseDeliveryCost(baseDeliveryCost);

        System.out.print("Enter No. of vehicles: ");
        int noOfVehicles = scanner.nextInt();
        System.out.print("Enter Max Speed: ");
        double maxSpeedValue = scanner.nextDouble();
        System.out.print("Enter Max Carriable weight: ");
        double maxCarriableWeight = scanner.nextDouble();
        Weight maxWeight = new Weight(maxCarriableWeight, WeightUnit.KG);
        Speed maxSpeed = new Speed(maxSpeedValue, SpeedUnit.KM_PER_HOUR);

        for (int i = 0; i < noOfVehicles; i++) {
            app.vehicleService.add(new Vehicle("V_"+i, maxWeight, maxSpeed));
        }

        System.out.print("No. of packages: ");
        int noOfPackages = scanner.nextInt();

        for (int i = 0; i < noOfPackages; i++) {
            PackageDto packageDto = inputPackageDetails(scanner);
            app.packageService.addPackage(packageDto);
        }
        app.packageService.calculateEstimationTimes(maxWeight, maxSpeed);

        List<Package> allPackages = app.packageService.getAllPackages();
        allPackages.forEach(ASAPApplication::printOutput);
    }

    private static void printOutput(Package aPackage) {
        Cost totalCost = aPackage.getTotalCost();
        Cost discount = aPackage.getDiscount();
        Time time = aPackage.getEstimatedTime();
        System.out.printf("PackageID: %s Discount: %s %s Total cost: %s %s Estimated delivery time(in hours): %s %s", aPackage.getId(), discount.getValue(), discount.getCurrency().name, totalCost.getValue(), totalCost.getCurrency().name, time.getValue(), time.getUnit().name);
        System.out.println();
    }

    private static PackageDto inputPackageDetails(Scanner scanner) {
        System.out.print("PackageID:");
        String packageId = scanner.next();
        System.out.print("Weight(in kgs):");
        double weightValue = scanner.nextDouble();
        System.out.print("Distance(in kms):");
        double distanceValue = scanner.nextDouble();
        System.out.print("offerCode:");
        String offerCode = scanner.next();
        return new PackageDto(packageId, new Weight(weightValue, WeightUnit.KG), new Distance(distanceValue, DistanceUnit.KM), offerCode);
    }

    private void setUp() {
        OfferRepository offerRepository = new OfferRepository();
        PackageRepository packageRepository = new PackageRepository();
        OfferService offerService = new OfferService(offerRepository);
        vehicleService = new VehicleService(new VehicleRepository());
        packageService = new PackageService(packageRepository, offerService, vehicleService);
    }
}
