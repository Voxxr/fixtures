package com.voxxr.fixtures.support.car;

import com.voxxr.fixtures.AbstractFixture;
import com.voxxr.fixtures.OrderedFixture;

public class AudiCarFixture extends AbstractFixture implements OrderedFixture {

    public static final String REF_NAME = "car-audi";

    public static final String CAR = "audi";

    @Override
    public void load() {
        setReference(REF_NAME, new Car(CAR));
    }

    @Override
    public int getOrder() {
        return 0;
    }
    
}
