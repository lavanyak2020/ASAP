package com.ee.asap.service;

import com.ee.asap.datalayer.OfferRepository;
import com.ee.asap.domain.constants.enums.DistanceUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteria;
import com.ee.asap.exception.OfferNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OfferServiceTest {
    private OfferRepository offerRepository;
    private OfferService offerService;

    @BeforeEach
    void setUp() {
        offerRepository = mock(OfferRepository.class);
        offerService = new OfferService(offerRepository);
    }

    @Test
    void shouldReturnOfferEntityOfGivenId() throws OfferNotFoundException {
        String testOfferId = "test-offer-id";
        DistanceAndWeightCriteria criteria = DistanceAndWeightCriteria.builder()
                                                                       .minDistance(new Distance(1, DistanceUnit.KM))
                                                                       .maxDistance(new Distance(199, DistanceUnit.KM))
                                                                       .minWeight(new Weight(70, WeightUnit.KG))
                                                                       .maxWeight(new Weight(200, WeightUnit.KG))
                                                                       .build();
        Offer testOffer = Offer.builder().id(testOfferId).discountPercentage(10).criteria(criteria).build();
        when(offerRepository.findById(testOfferId)).thenReturn(testOffer);

        Offer actualOffer = offerService.getById(testOfferId);

        assertThat(actualOffer, is(equalTo(testOffer)));
    }

    @Test
    void shouldThrowOfferNotFoundExceptionIfOfferNotFoundForGivenId() throws OfferNotFoundException {
        String testOfferId = "test-offer-id";
        when(offerRepository.findById(testOfferId)).thenThrow(new OfferNotFoundException("Offer not found"));

        assertThrows(OfferNotFoundException.class, () -> offerService.getById(testOfferId));
    }
}