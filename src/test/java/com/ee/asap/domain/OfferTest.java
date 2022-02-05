package com.ee.asap.domain;

import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteria;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteriaInput;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.DistanceUnit;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.domain.model.WeightUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OfferTest {

    private DistanceAndWeightCriteria criteria;
    private Offer offer;

    @BeforeEach
    public void setUp() {
        criteria = mock(DistanceAndWeightCriteria.class);
        offer = Offer.builder().id("OFFER_1")
                     .discountPercentage(10f)
                     .criteria(criteria)
                     .build();
    }

    @Test
    void shouldReturnTrueIfInputMetOfferCriteria() {
        DistanceAndWeightCriteriaInput input = new DistanceAndWeightCriteriaInput(new Distance(1f, DistanceUnit.KM), new Weight(1f, WeightUnit.KG));
        when(criteria.validateFor(input)).thenReturn(true);

        assertTrue(offer.isApplicableFor(input));
        verify(criteria, times(1)).validateFor(input);
    }

    @Test
    void shouldReturnFalseIfInputNotMetOfferCriteria() {
        DistanceAndWeightCriteriaInput input = new DistanceAndWeightCriteriaInput(new Distance(1f, DistanceUnit.KM), new Weight(1f, WeightUnit.KG));
        when(criteria.validateFor(input)).thenReturn(false);

        assertFalse(offer.isApplicableFor(input));
        verify(criteria, times(1)).validateFor(input);
    }

    @Test
    void shouldEquateTwoSameOffersByCriteriaAndDiscount() {
        Offer offer1 = Offer.builder().id("OFFER_1")
                             .discountPercentage(10f)
                             .criteria(criteria)
                             .build();
        Offer offer2 = Offer.builder().id("OFFER_2")
                            .discountPercentage(10f)
                            .criteria(criteria)
                            .build();

        assertThat(offer1, is(equalTo(offer2)));
    }
}
