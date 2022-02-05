package com.ee.asap.mapper;

import com.ee.asap.domain.model.Package;
import com.ee.asap.dto.PackageDto;

public class PackageMapper {

    public static Package toDomainEntity(PackageDto packageDto) {
        return new Package(packageDto.getId(), packageDto.getWeight(), packageDto.getDistanceToDestination());
    }
}
