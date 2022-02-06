package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.Currency;
import com.ee.asap.domain.constants.enums.DistanceUnit;
import com.ee.asap.domain.constants.enums.TimeUnit;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteriaInput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static com.ee.asap.domain.constants.CostConstants.DISTANCE_COST_FACTOR;
import static com.ee.asap.domain.constants.CostConstants.WEIGHT_COST_FACTOR;

@RequiredArgsConstructor
@Getter
public class Package {
    private final String id;
    private final Weight weight;
    private final Distance distanceToDestination;
    private Cost totalCost;
    private Cost discount;
    private Time estimatedTime;

    public void calculateTotalCostWith(Cost baseDeliveryCost, Offer appliedOffer) {
        Cost actualCost = actualCost(baseDeliveryCost);
        calculateDiscount(actualCost, appliedOffer);
        totalCost = actualCost.minus(discount);
    }

    public void calculateTotalCostWith(Cost baseDeliveryCost) {
        Cost actualCost = actualCost(baseDeliveryCost);
        discount = new Cost(0, Currency.RUPEE);
        totalCost = actualCost.minus(discount);
    }

    private Cost actualCost(Cost baseDeliveryCost) {
        double value = baseDeliveryCost.getValue()
                + (weight.getValue() * WEIGHT_COST_FACTOR)
                + (distanceToDestination.getValue() * DISTANCE_COST_FACTOR);
        return new Cost(value, Currency.RUPEE);
    }

    private void calculateDiscount(Cost actualCost, Offer appliedOffer) {
        if (appliedOffer.isApplicableFor(new DistanceAndWeightCriteriaInput(distanceToDestination, weight))) {
            discount = new Cost(actualCost.getValue() * (appliedOffer.getDiscountPercentage() / 100), Currency.RUPEE);
        } else {
            discount = new Cost(0, Currency.RUPEE);
        }
    }

    public void addEstimatedTime(Time currentTime, Speed speed) {
        estimatedTime = currentTime.plus(new Time((distanceToDestination.getValue() / speed.getValue()), TimeUnit.HOURS));
    }

    public static List<Package> getEfficientComboOfSize(List<Package> packages, Weight maxWeight, Speed speed, int size) {
        List<List<Package>> combos = generatePackageCombosWhoseWeightIsNotMoreThanMaxVehicleCapacityOfSize(packages, maxWeight, size);

        return extractEfficientPackageCombo(combos);
    }

    public static List<Package> packagesToEstimate(List<Package> packages) {
        return packages.stream().filter(aPackage -> aPackage.getEstimatedTime() == null).collect(Collectors.toList());
    }

    private static List<Package> extractEfficientPackageCombo(List<List<Package>> combos) {
        if (combos.size() == 0) {
            return null;
        }
        List<Package> bestCombo = combos.get(0);

        for (List<Package> combo : combos) {
            double currentComboTotalWeight = getTotalWeightOf(combo);
            double bestComboTotalWeight = getTotalWeightOf(bestCombo);
            double currentComboDistanceOfLongestDestination = getLongestDistance(combo);
            double bestComboDistanceOfLongestDestination = getLongestDistance(bestCombo);
            if (currentComboTotalWeight > bestComboTotalWeight) {
                bestCombo = combo;
            } else if (currentComboTotalWeight == bestComboTotalWeight && currentComboDistanceOfLongestDestination < bestComboDistanceOfLongestDestination) {
                bestCombo = combo;
            }
        }
        return bestCombo;
    }

    private static double getLongestDistance(List<Package> packages) {
        OptionalDouble max = packages.stream().mapToDouble(aPackage -> aPackage.getDistanceToDestination().getValue()).max();
        return max.isPresent() ? max.getAsDouble() : Integer.MAX_VALUE;
    }

    public static Distance getShipmentDistanceFor(List<Package> packages) {
        return new Distance(getLongestDistance(packages), DistanceUnit.KM);
    }

    private static double getTotalWeightOf(List<Package> bestCombo) {
        return bestCombo.stream().mapToDouble(aPackage -> aPackage.getWeight().getValue()).sum();
    }

    private static List<List<Package>> generatePackageCombosWhoseWeightIsNotMoreThanMaxVehicleCapacityOfSize(List<Package> packages, Weight maxWeight, int comboSize) {
        List<int[]> nCrIndices = generate(packages.size(), comboSize);
        List<List<Package>> combos = nCrIndices.stream().map(combination -> {
            List<Package> packagesCombo = new ArrayList<>();
            for (int index : combination) {
                packagesCombo.add(packages.get(index));
            }
            return packagesCombo;
        }).collect(Collectors.toList());

        List<List<Package>> validCombos = combos.stream().filter(combination -> {
            double weightSum = 0;
            for (Package aPackage : combination) {
                weightSum += aPackage.getWeight().getValue();
            }
            return !(weightSum > maxWeight.getValue());
        }).collect(Collectors.toList());

        return validCombos;
    }

    private static void helper(List<int[]> combinations, int[] data, int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }

    public static List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n - 1, 0);
        return combinations;
    }
}
