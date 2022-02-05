package com.ee.asap.datalayer;

import com.ee.asap.domain.constants.enums.DistanceUnit;
import com.ee.asap.domain.constants.enums.WeightUnit;
import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.domain.model.Weight;
import com.ee.asap.domain.model.criteria.DistanceAndWeightCriteria;
import com.ee.asap.exception.OfferNotFoundException;

import java.util.Map;

public class OfferRepository {
    private final Map<String, Offer> offers;

    public OfferRepository() {
        DistanceAndWeightCriteria criteria1 = DistanceAndWeightCriteria.builder()
                                                                       .minDistance(new Distance(1, DistanceUnit.KM))
                                                                       .maxDistance(new Distance(199, DistanceUnit.KM))
                                                                       .minWeight(new Weight(70, WeightUnit.KG))
                                                                       .maxWeight(new Weight(200, WeightUnit.KG))
                                                                       .build();
        Offer ofr001 = Offer.builder().id("OFR001").discountPercentage(10).criteria(criteria1).build();

        DistanceAndWeightCriteria criteria2 = DistanceAndWeightCriteria.builder()
                                                                       .minDistance(new Distance(50, DistanceUnit.KM))
                                                                       .maxDistance(new Distance(150, DistanceUnit.KM))
                                                                       .minWeight(new Weight(100, WeightUnit.KG))
                                                                       .maxWeight(new Weight(250, WeightUnit.KG))
                                                                       .build();
        Offer ofr002 = Offer.builder().id("OFR002").discountPercentage(7).criteria(criteria2).build();

        DistanceAndWeightCriteria criteria3 = DistanceAndWeightCriteria.builder()
                                                                       .minDistance(new Distance(50, DistanceUnit.KM))
                                                                       .maxDistance(new Distance(250, DistanceUnit.KM))
                                                                       .minWeight(new Weight(10, WeightUnit.KG))
                                                                       .maxWeight(new Weight(150, WeightUnit.KG))
                                                                       .build();
        Offer ofr003 = Offer.builder().id("OFR003").discountPercentage(5).criteria(criteria3).build();

        offers = Map.of(ofr001.getId(), ofr001, ofr002.getId(), ofr002, ofr003.getId(), ofr003);
    }

    public Offer findById(String id) throws OfferNotFoundException {
        if(!offers.containsKey(id)) {
            throw new OfferNotFoundException(String.format("Offer with id (%s) is not found in ASAP", id));
        }
        return offers.get(id);
    }
}
