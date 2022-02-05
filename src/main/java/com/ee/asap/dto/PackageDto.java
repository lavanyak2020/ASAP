package com.ee.asap.dto;

import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.Weight;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PackageDto {
    private final Weight weight;
    private final Distance distanceToDestination;
    private final String offerId;
}
