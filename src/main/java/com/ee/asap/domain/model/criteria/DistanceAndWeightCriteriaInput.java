package com.ee.asap.domain.model.criteria;

import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Weight;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DistanceAndWeightCriteriaInput implements CriteriaInput {
    private Distance distance;
    private Weight weight;
}
