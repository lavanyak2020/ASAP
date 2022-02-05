package com.ee.asap;

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
import com.ee.asap.service.OfferService;
import com.ee.asap.service.PackageService;

import java.util.Scanner;

public class ASAPApplication {
    private PackageService packageService;

    public ASAPApplication() {
        setUp();
    }

    public static void main(String[] args) {
        ASAPApplication app = new ASAPApplication();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter Base delivery cost: ");
        double baseDeliveryCostValue = scanner.nextDouble();
        Cost baseDeliveryCost = new Cost(baseDeliveryCostValue, Currency.RUPEE);
        app.packageService.setBaseDeliveryCost(baseDeliveryCost);

        System.out.print("No. of packages: ");
        int noOfPackages = scanner.nextInt();

        for(int i = 0; i < noOfPackages; i++) {
            PackageDto packageDto = inputPackageDetails(scanner);
            Package aPackage = app.packageService.addPackage(packageDto);
            printOutput(aPackage);
        }
    }

    private static void printOutput(Package aPackage) {
        Cost totalCost = aPackage.getTotalCost();
        Cost discount = aPackage.getDiscount();
        System.out.printf("Total cost: %s %s Discount: %s %s", totalCost.getValue(), totalCost.getCurrency().name, discount.getValue(), discount.getCurrency().name);
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
        packageService = new PackageService(packageRepository, offerService);
    }
}
