package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.Currency;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteriaInput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
        if(appliedOffer.isApplicableFor(new DistanceAndWeightCriteriaInput(distanceToDestination, weight))) {
            discount = new Cost(actualCost.getValue() * (appliedOffer.getDiscountPercentage() / 100), Currency.RUPEE);
        } else {
            discount = new Cost(0, Currency.RUPEE);
        }
    }
}
