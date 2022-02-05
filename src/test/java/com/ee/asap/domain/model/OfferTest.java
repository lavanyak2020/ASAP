package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.WeightUnit;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteria;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteriaInput;
import com.ee.asap.domain.constants.enums.DistanceUnit;
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
        offer = Offer.builder().code("OFFER_1")
                     .discountPercentage(10)
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
        Offer offer1 = Offer.builder().code("OFFER_1")
                            .discountPercentage(10)
                            .criteria(criteria)
                            .build();
        Offer offer2 = Offer.builder().code("OFFER_2")
                            .discountPercentage(10)
                            .criteria(criteria)
                            .build();

        assertThat(offer1, is(equalTo(offer2)));
    }
}
