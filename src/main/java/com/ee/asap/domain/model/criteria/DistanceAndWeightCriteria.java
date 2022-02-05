package com.ee.asap.domain.model.criteria;

import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Weight;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class DistanceAndWeightCriteria implements Criteria {
    private Distance minDistance;
    private Distance maxDistance;
    private Weight minWeight;
    private Weight maxWeight;

    @Override
    public boolean validateFor(CriteriaInput criteriaInput) {
        DistanceAndWeightCriteriaInput input = (DistanceAndWeightCriteriaInput) criteriaInput;
        return validateDistance(input.getDistance()) && validateWeight(input.getWeight());
    }

    private boolean validateDistance(Distance inputDistance) {
        return inputDistance.isGreaterOrEqual(minDistance) && inputDistance.isLessOrEqual(maxDistance);
    }

    private boolean validateWeight(Weight inputWeight) {
        return inputWeight.isGreaterOrEqual(minWeight) && inputWeight.isLessOrEqual(maxWeight);
    }
}
