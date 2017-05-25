package com.voxxr.fixtures.support.car;

import com.voxxr.fixtures.AbstractFixture;

public class VolvoCarFixture extends AbstractFixture {

    public static final String REF_NAME = "car-volvo";

    public static final String CAR = "volvo";

    @Override
    public void load() {
        setReference(REF_NAME, new Car(CAR));
    }

}
