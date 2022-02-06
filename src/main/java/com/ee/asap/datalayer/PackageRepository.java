package com.ee.asap.datalayer;

import com.ee.asap.domain.model.Package;

import java.util.ArrayList;
import java.util.List;

public class PackageRepository {
    private final List<Package> packages;

    public PackageRepository() {
        this.packages = new ArrayList<>();
    }

    public void add(Package aPackage) {
        packages.add(aPackage);
    }

    public List<Package> findAll() {
        return packages;
    }
}
