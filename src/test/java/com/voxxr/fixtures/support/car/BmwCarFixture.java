package com.voxxr.fixtures.support.car;

import com.voxxr.fixtures.AbstractFixture;
import com.voxxr.fixtures.DependentFixture;
import com.voxxr.fixtures.Fixture;

import java.util.Arrays;
import java.util.List;

public class BmwCarFixture extends AbstractFixture implements DependentFixture {

    public static final String REF_NAME = "car-bmw";

    public static final String CAR = "bmw";

    @Override
    public void load() {
        setReference(REF_NAME, new Car(CAR));
    }

    @Override
    public List<Class<? extends Fixture>> getDependencies() {
        return Arrays.asList(AudiCarFixture.class);
    }

}
