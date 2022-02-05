package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.Currency;
import com.ee.asap.domain.constants.enums.DistanceUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PackageTest {

    @Test
    void shouldDiscountBe0IfAppliedOfferIsInvalidForGivenPackage() {
        Offer offer = mock(Offer.class);
        when(offer.isApplicableFor(Mockito.any())).thenReturn(false);
        Weight packageWeight = new Weight(10, WeightUnit.KG);
        Distance distanceToDestination = new Distance(50, DistanceUnit.KM);
        Package receivedPackage = new Package("PKG1", packageWeight, distanceToDestination, offer);

        receivedPackage.calculateTotalCostWith(new Cost(100, Currency.RUPEE));

        Cost zeroRupees = new Cost(0, Currency.RUPEE);
        assertThat(receivedPackage.getDiscount(), is(equalTo(zeroRupees)));
    }

    @Test
    void shouldTotalCostBeSubtractionOfActualCostAndDiscountIfOfferIsValid() {
        Offer offer = mock(Offer.class);
        when(offer.isApplicableFor(Mockito.any())).thenReturn(true);
        when(offer.getDiscountPercentage()).thenReturn(Double.valueOf(5));
        Weight packageWeight = new Weight(10, WeightUnit.KG);
        Distance distanceToDestination = new Distance(50, DistanceUnit.KM);
        Package receivedPackage = new Package("PKG1", packageWeight, distanceToDestination, offer);

        receivedPackage.calculateTotalCostWith(new Cost(100, Currency.RUPEE));

        Cost twentyTwoAndHalfRupees = new Cost(22.5, Currency.RUPEE);
        Cost fourHundredTwentySevenAndHalfRupees = new Cost(427.5, Currency.RUPEE);
        assertThat(receivedPackage.getDiscount(), is(equalTo(twentyTwoAndHalfRupees)));
        assertThat(receivedPackage.getTotalCost(), is(equalTo(fourHundredTwentySevenAndHalfRupees)));
    }
}