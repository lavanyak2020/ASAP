package com.ee.asap.service;

import com.ee.asap.datalayer.OfferRepository;
import com.ee.asap.domain.model.Offer;
import com.ee.asap.exception.OfferNotFoundException;

public class OfferService {
    public final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer getById(String id) throws OfferNotFoundException {
        return offerRepository.findById(id);
    }
}
