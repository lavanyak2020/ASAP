package com.ee.asap.domain.model;

import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteria;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteriaInput;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.DistanceUnit;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.domain.model.WeightUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DistanceAndWeightCriteriaTest {
    private DistanceAndWeightCriteria criteria;

    @BeforeEach
    public void setUp() {
        Distance minDistance = new Distance(20f, DistanceUnit.KM);
        Distance maxDistance = new Distance(100f, DistanceUnit.KM);
        Weight minWeight = new Weight(5f, WeightUnit.KG);
        Weight maxWeight = new Weight(10f, WeightUnit.KG);
        criteria = DistanceAndWeightCriteria.builder()
                                            .minDistance(minDistance)
                                            .maxDistance(maxDistance)
                                            .minWeight(minWeight)
                                            .maxWeight(maxWeight)
                                            .build();
    }

    @Test
    void shouldReturnTrueIfInputArgsMetCriteria() {
        Distance inputDistance = new Distance(50f, DistanceUnit.KM);
        Weight inputWeight = new Weight(6, WeightUnit.KG);
        DistanceAndWeightCriteriaInput input = new DistanceAndWeightCriteriaInput(inputDistance, inputWeight);

        boolean result = criteria.validateFor(input);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseIfInputDistanceDidNotMetCriteria() {
        Distance inputDistance = new Distance(10f, DistanceUnit.KM);
        Weight inputWeight = new Weight(6f, WeightUnit.KG);
        DistanceAndWeightCriteriaInput input = new DistanceAndWeightCriteriaInput(inputDistance, inputWeight);

        boolean result = criteria.validateFor(input);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfInputWeightDidNotMetCriteria() {
        Distance inputDistance = new Distance(50f, DistanceUnit.KM);
        Weight inputWeight = new Weight(1f, WeightUnit.KG);
        DistanceAndWeightCriteriaInput input = new DistanceAndWeightCriteriaInput(inputDistance, inputWeight);

        boolean result = criteria.validateFor(input);

        assertFalse(result);
    }
}