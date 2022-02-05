package com.ee.asap.domain.model;

import com.ee.asap.domain.model.criteria.Criteria;
import com.ee.asap.domain.model.criteria.CriteriaInput;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Objects;

@Builder
@AllArgsConstructor
public class Offer {
    private String id;
    private float discountPercentage;
    private Criteria criteria;

    public boolean isApplicableFor(CriteriaInput input) {
        return criteria.validateFor(input);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Float.compare(offer.discountPercentage, discountPercentage) == 0
                && criteria.equals(offer.criteria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discountPercentage, criteria);
    }
}
